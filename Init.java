package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

/** The Tree for Gitlet Project.
 * @author Rowan P.
 */
public class Init implements Serializable {

    /******** Variable Declaration ********/

    /** HashMap of branches. */
    private HashMap<String, String> _branches;

    /** String head pointer. */
    private String _head;

    /** HashMap of staging folders. */
    private HashMap<String, String> _readyForCommit;

    /** List of un-tracked files. */
    private ArrayList<String> _untracked;



    /** Init method. */
    Init() {
        _head = "master";
        _branches = new HashMap<>();
        String msg = "initial commit";
        InitCommit initCommit = new InitCommit(null,
                null, msg, true);

        new File(".gitlet").mkdirs();
        new File(".gitlet/commits").mkdirs();
        new File(".gitlet/staging").mkdirs();

        File initFile = new File(".gitlet/commits/"
                + initCommit.getUID());

        initHelper(initCommit, initFile);

    }



    /** Add Method: Takes in a String adding and add the file to the staging
     * stage (ready to commit).
     * @param filename file name
     */
    public void add(String filename) {
        File addingFile = new File(filename);

        dneException(addingFile);

        String hashingAddFile =
                Utils.sha1(Utils.readContentsAsString(addingFile));
        InitCommit mostRecentCommit = uidToString(_branches.get(_head));
        HashMap<String, String> curFiles =
                mostRecentCommit.getCommitFiles();
        File stageFiles = new File(".gitlet/staging/" + hashingAddFile);

        addHelper1(curFiles, filename, stageFiles, hashingAddFile, addingFile);

        _untracked.remove(filename);
    }



    /** Commit Method: Takes in a String MSG and commit the file.
     * @param commitMSG string of commit message
     */
    public void commit(String commitMSG) {
        emptyCommitBaseCase(commitMSG);

        InitCommit mostRecentCommit = uidToString(_branches.get(_head));
        HashMap<String, String> trackFile =
                mostRecentCommit.getCommitFiles();

        if (trackFile == null) {
            trackFile = new HashMap<>();
        }

        commitHelper1(trackFile);

        parentCommitHelper(mostRecentCommit, trackFile, commitMSG);

    }


    /** Global Log: This function only simple print out
     * all the committed commits. */
    void globalLog() {
        File commitDir = new File(".gitlet/commits");
        File[] allCommits = commitDir.listFiles();
        if (allCommits != null) {
            for (File curFile : allCommits) {
                printLog(curFile.getName());
            }
        }
    }


    /** Log Method: display the log history. */
    void logCommits() {
        String curCommit = _branches.get(_head);
        while (curCommit != null) {
            InitCommit prevCommit = uidToString(curCommit);
            printLog(curCommit);
            curCommit = prevCommit.getParentID();
        }
    }



    /** Remove Method: Take in a string of message when removing the file,
     * this function mainly to un-stage the file if it is staged at the time
     * of removal.
     * @param filename name of file in string
     */
    public void rm(String filename) {
        InitCommit mostRecentCommit = uidToString(_branches.get(_head));
        File removingFiles = new File(filename);
        HashMap<String, String> trackingFiles =
                mostRecentCommit.getCommitFiles();

        rmhelper1(filename, trackingFiles, removingFiles);

        rmhelper2(filename, trackingFiles);
    }



    /** Checkout Method: take in string of args
     * and check out to the previous commit.
     * @param commitArr commit array */
    public void checkout(String[] commitArr) {
        String commitUID; String filename;
        String first = commitArr[0];
        String second = commitArr[1];
        int arrLen = commitArr.length;

        if (arrLen == 2) {
            filename = second; commitUID = _branches.get(_head);
        } else if (arrLen == 3) {
            commitUID = first;
            filename = commitArr[2];
        } else {
            throw new GitletException();
        }

        InitCommit committing = uidToString(commitUID);
        HashMap<String, String> trackingFiles = committing.getCommitFiles();

        firstCheckoutHelper(filename, trackingFiles);
    }

    /** Checkout the branch and switch to new branch.
     * @param branch branch name
     */
    public void checkout(String branch) {
        /** base cases of this method. **/
        checkoutBaseCases2(branch);

    }

    /** Status Method: This will print out the status of a repository.
         * whether or not it has ready file to commit
         * or not ready so we can add it to commit.*/
    public void status() {
        System.out.println("=== Branches ===");
        statusBranches();
        System.out.println();

        System.out.println("=== Staged Files ===");
        statusHelperStage();
        System.out.println();

        System.out.println("=== Removed Files ===");
        statushelperRemove();
        System.out.println();

        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();

        System.out.println("=== Untracked Files ===");
        System.out.println();

    }




    /*********    Helper Methods   *********/


    /**
     * Returns the uid of the current head.
     * @return head
     */
    String getHead() {
        return _branches.get(_head);
    }

    /** Convert UID to Commit.
     * @param uid uid string.
     * @return commit version.
     */
    InitCommit uidToString(String uid) {
        File findFile = new File(".gitlet/commits/" + uid);
        if (findFile.exists()) {
            return Utils.readObject(findFile, InitCommit.class);
        } else {
            Utils.message("No commit with that id exists.");
            throw new GitletException();
        }
    }

    /**
     * Print out the commit with the uid.
     * Two types of commit: regular and merge
     * @param uid is universal ID
     */
    private void printLog(String uid) {
        InitCommit thisCommit = uidToString(uid);
        if (thisCommit.getParent() != null
                && thisCommit.getParent().length > 1) {

            printLogIfCase(thisCommit, uid);

        } else {

            printLogElseCase(thisCommit, uid);
        }
    }

    /** helper to simplify if case in printLog function.
     *
     * @param thisCommit this commit
     * @param uid the universal id
     */
    private void printLogIfCase(InitCommit thisCommit, String uid) {
        System.out.println("===");
        System.out.println("commit " + uid);
        String firstMerge = thisCommit.getParent()[0].substring(0, 7);
        String secondMerge = thisCommit.getParent()[1].substring(0, 7);
        System.out.println("Merge: " + firstMerge + " " + secondMerge);
        System.out.println("Date: " + thisCommit.getCommitTime());
        System.out.println(thisCommit.getCommitMsg());
        System.out.println();
    }

    /** helper to simplify else case in printLog function.
     *
     * @param thisCommit this commit
     * @param uid uid
     */
    private void printLogElseCase(InitCommit thisCommit, String uid) {
        System.out.println("===");
        System.out.println("commit " + uid);
        System.out.println("Date: " + thisCommit.getCommitTime());
        System.out.println(thisCommit.getCommitMsg());
        System.out.println();
    }

    /** check for existence when adding files.
     *
     * @param add file to be added
     */
    void dneException(File add) {
        if (!add.exists()) {
            Utils.message("File does not exist.");
            throw new GitletException();
        }
    }

    /** Helper for init method.
     *
     * @param initC initial commit
     * @param initial initial file
     */
    private void initHelper(InitCommit initC, File initial) {
        Utils.writeContents(initial,
                (Object) Utils.serialize(initC));
        _branches.put(_head, initC.getUID());
        _readyForCommit = new HashMap<>();
        _untracked = new ArrayList<>();
    }

    /** Helper function to commit method.
     * @param trackingfile tracking file
     */
    private void commitHelper1(HashMap<String, String> trackingfile) {
        if (_readyForCommit.size() != 0
                | _untracked.size() != 0) {
            for (String fileName : _readyForCommit.keySet()) {
                trackingfile.put(fileName, _readyForCommit.get(fileName));
            }
            for (String fileName : _untracked) {
                trackingfile.remove(fileName);
            }
        } else {
            Utils.message("No changes added to the commit.");
            throw new GitletException();
        }
    }

    /** Helper for parent commit files.
     *
     * @param mostRecentCommit most recent commit
     * @param trackingfile file to be tracked
     * @param message commit message
     */
    private void parentCommitHelper(InitCommit mostRecentCommit,
                                    HashMap<String, String> trackingfile,
                                    String message) {
        String[] parentCommit = new String[]{mostRecentCommit.getUID()};
        InitCommit committing = new InitCommit(trackingfile,
                parentCommit, message,  false);
        File commitFiles = new File(".gitlet/commits/" + committing.getUID());
        Utils.writeObject(commitFiles, committing);
        _readyForCommit = new HashMap<>();
        _untracked = new ArrayList<>();
        _branches.put(_head, committing.getUID());
    }

    /** base case of empty commit message.
     *
     * @param message commit message
     */
    private void emptyCommitBaseCase(String message) {
        if (message.equals("")) {
            Utils.message("Please enter a commit message.");
            throw new GitletException();
        }
    }


    /** Helper function to add method.
     *
     * @param current current file
     * @param name file name
     * @param staged files for staging
     * @param hashing hashing add file
     * @param add file to add
     */
    void addHelper1(HashMap<String, String> current, String name,
                    File staged, String hashing,
                    File add) {
        if (current == null
                || !current.containsKey(name)
                || !current.get(name).equals(hashing)) {
            _readyForCommit.put(name, hashing);
            Utils.writeContents(staged,
                    Utils.readContentsAsString(add));
        }
    }

    /** Helper function 1 to rm method.
     *
     * @param name file name
     * @param files files to be tracked
     * @param removed files to be removed
     */
    private void rmhelper1(String name,
                           HashMap<String, String> files,
                           File removed) {
        if (!removed.exists()) {
            if (files != null) {
                if (!files.containsKey(name)) {
                    Utils.message("File does not exist.");
                    throw new GitletException();
                }
            }
        }
    }

    /** Helper function 2 to rm method.
     *
     * @param name file name
     * @param file tracking files
     */
    private void rmhelper2(String name,
                           HashMap<String, String> file) {
        if (_readyForCommit.containsKey(name)) {
            _readyForCommit.remove(name);

        } else if (file != null) {
            if (file.containsKey(name)) {
                _untracked.add(name);
                Utils.restrictedDelete(new File(name));
            }
        } else {
            Utils.message("No reason to remove the file.");
            throw new GitletException();
        }
    }

    /** helper for status method on staged files.
     *
     */
    private void statusHelperStage() {
        Object[] curStages = _readyForCommit.keySet().toArray();
        Arrays.sort(curStages);
        for (Object staging : curStages) {
            System.out.println(staging);
        }
    }

    /** helper for status method on removed file.
     *
     */
    private void statushelperRemove() {
        Object[] notTrackedFiles = _untracked.toArray();
        Arrays.sort(notTrackedFiles);
        for (Object removingFiles : notTrackedFiles) {
            System.out.println(removingFiles);
        }
    }

    /** helper for the first checkout method.
     *
     * @param name file name
     * @param file file to be tracked
     */
    private void firstCheckoutHelper(String name,
                                     HashMap<String, String> file) {
        if (file != null) {
            if (!file.containsKey(name)) {
                Utils.message("File does not exist in that commit.");
                throw new GitletException();
            } else {
                File fileInCommit = new File(name);
                String stagingFiles = ".gitlet/staging/"
                        + file.get(name);
                File readyCommitFiles = new File(stagingFiles);
                Utils.writeContents(fileInCommit,
                        Utils.readContentsAsString(readyCommitFiles));
            }
        } else {
            Utils.message("File does not exist in that commit.");
            throw new GitletException();
        }
    }

    /** helper to take care of branches part of status method.
     *
     */
    private void statusBranches() {
        Object[] curKeys = _branches.keySet().toArray();
        Arrays.sort(curKeys);
        for (Object curBranch : curKeys) {
            if (curBranch.equals(_head)) {
                System.out.println("*" + curBranch);
            } else {
                System.out.println(curBranch);
            }
        }
    }



    /** base cases coverage for checkout 2 method.
     * @param branch branch
     */
    private void checkoutBaseCases2(String branch) {
        if (_head.equals(branch)) {
            Utils.message("No need to checkout the current branch.");
            throw new GitletException();
        }
        if (!_branches.containsKey(branch)) {
            Utils.message("No such branch exists.");
            throw new GitletException();
        }
    }


}
