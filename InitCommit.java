package gitlet;
import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;

/**
 * The class for the initial/basic commit.
 * @author Rowan P.
 */
class InitCommit implements Serializable {


    /**Initialize (init) a commit with commit message, files to commit,
     * parent, and boolean for whether initialized.
     * FILES being committed
     * PARENT its parent
     * MESSAGE committing message
     * INITIALIZED check if it is init commit
     */
    InitCommit(HashMap<String, String> files,
               String[] parent, String message, boolean initialized) {
        _commitMsg = message;
        _parents = parent;
        _fileToCommit = files;

        if (initialized) {
            _commitTime = "Mon May 4 16:00:00 2020 -0800";
        } else {
            _commitTime = FORMATDATETIME.format(new Date());
        }

        String finalUID; String parents;
        if (_parents == null) {
            parents = "";
        } else {
            parents = Arrays.toString(_parents);
        }
        if (_fileToCommit == null) {
            finalUID = Utils.sha1(_commitMsg, "",
                    _commitTime, parents);
        } else {
            finalUID = Utils.sha1(_commitMsg, _fileToCommit.toString(),
                    _commitTime, parents);
        }

        _uid = finalUID;
    }


    /******  Getter/Accessor Functions  *****/

    /** Get commit message.
     * @return committing message
     */
    String getCommitMsg() {
        return _commitMsg;
    }

    /** get the files that we are committing.
     * @return initial file to commit.
     */
    HashMap<String, String> getCommitFiles() {
        return _fileToCommit;
    }

    /** Get time commit.
     * @return time needed to commit
     */
    String getCommitTime() {
        return _commitTime;
    }

    /** Get parent.
     * @return its parent
     */
    String[] getParent() {
        return _parents;
    }

    /** Get parent id.
     * @return its parent's ID
     */
    String getParentID() {
        if (_parents != null) {
            return _parents[0];
        }
        return null;
    }

    /** Get UID.
     * @return its uid
     */
    String getUID() {
        return _uid;
    }


    /***  Variable declaration ***/

    /** String of commit message.*/
    private String _commitMsg;

    /** HashMap of file to commit.*/
    private HashMap<String, String> _fileToCommit;

    /** String of commit time.*/
    private String _commitTime;

    /** String array of parents. */
    private String[] _parents;

    /** String of UID. */
    private String _uid;

    /** Format of date time year.*/
    private static final SimpleDateFormat FORMATDATETIME =
            new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
}
