package miljoboven;

import java.util.ArrayList;
import java.util.ListIterator;


/**
 *
 * @author Johan
 * @author Kristoffer
 * @version 2015-05-22
 */
public class CaseHandler {

    private ArrayList<Case> caseList;
    private DBHandler dbHandler;
    private NotificationHandler notificationHandler;
    private User user;

    public CaseHandler() {
        dbHandler = new DBHandler();
        caseList = dbHandler.fetchCases();
        notificationHandler = new NotificationHandler();
    }

    /**
     * Returns the list of the current Cases.
     * @return List of current Cases.
     */
    public ArrayList<Case> getCaseList() {
        caseList = dbHandler.fetchCases();
        if((user.getDepartment().equals(DepartmentsE.UNKNOWN))){
            return caseList;
            
        } else{
            ArrayList<Case> caseListTmp = new ArrayList<>();
            for (Case c : caseList) {
                if (c.getDepartment() == user.getDepartment()) {
                    caseListTmp.add(c);
                }
            }   
            return caseListTmp;
        }   
    }

    public void setCaseList(ArrayList<Case> caseList) {
        this.caseList = caseList;
    }

    public DBHandler getDbHandler() {
        return dbHandler;
    }

    public void setDbHandler(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public NotificationHandler getNotificationHandler() {
        return notificationHandler;
    }

    public void setNotificationHandler(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    public User getUser() {
        return user;
    }
    
    public UserTypeE getUserType() {
        return user.getUserType();
    }
    
    /**
     * Returns Case object with id. If no case with the id exists the method
     * will return null.
     * @param id
     * @return Selected Case or null if no Case with ID is found.
     */
    public Case getCase(int id) {
        Case selectedCase = null;
        for (Case c : caseList) {
            if (c.getCaseID() == id) {
                selectedCase = c;
            }
        }
        return selectedCase;
    }
    
    /**
     * Creates a new Case-object and adds it to the dbHandler.
     * If not logged in no object is created.
     * @param location Location of the violation
     * @param violationType Type of violation
     * @param date Date when the violation was found
     * @param citizenName Name of citizen that has reported the violation
     * @param citizenTele Telephone number of citizen that has reported the violation
     * @param misc Miscellaneous information
     * @return caseID of new Case, 0 if no object was created
     */
    public int addCase(String location, String violationType, String date,
            String citizenName, String citizenTele, String misc) {
        Case c = new Case(location, violationType, date, citizenName, citizenTele, misc);
        int caseID = c.getCaseID();
        dbHandler.addCase(c);
        caseList.add(c);
        return caseID;
    }

    /**
     * Saves the updated case
     * 
     * @param currentCase
     * @param department
     * @param misc
     * @return
     */
    public boolean saveCase(Case currentCase, DepartmentsE department, String misc) {
        currentCase.setDepartment(department);
        currentCase.setMisc(misc);
        boolean success = dbHandler.saveCase(currentCase);             
        notificationHandler.sendNotification(currentCase);
        return success;
    }
    
    /**
     * Log in user.
     * @param username
     * @param password
     * @return TODO
     */
    public boolean login(String username, char[] password) {
        user = SecurityCheck.verifyLogin(username, password);
        if(user != null){
            return true;
        }
        return false;
    }
    
    /**
     * Log out user.
     */
    public void confirmLogout() {
        user = null;
    }

    /**
     * Deletes a case
     * @param CurrentCase
     */
    void deleteCase(Case CurrentCase) {
        ListIterator listIterator = caseList.listIterator();
        while(listIterator.hasNext()){
            Object o = listIterator.next();
            if(o.equals(CurrentCase)){
                listIterator.remove();
            }
        }
        dbHandler.removeCase(CurrentCase);
    }
}
