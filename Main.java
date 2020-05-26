package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Rowan P.
 *  collaborator: Malika Sugathapala
 */
public class Main {

    /**  path file for my repo. */
    private static final String PATH = ".gitlet/rowanrepo";

    /** Array of valid commands for Gitlet. */
    private static ArrayList<String> validCommands =
            new ArrayList<>(Arrays.asList(
            "init",
            "add",
            "commit",
            "log",
            "global-log",
            "status",
            "rm",
            "checkout"));

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        try {
            if (args.length == 0) {
                throw new GitletException("Please enter a command.");
            }
            if (!validCommands.contains(args[0])) {
                throw new GitletException("No command with that name exists.");
            } else {
                String[] enteredCommand = Arrays.copyOfRange(args,
                        1, args.length);
                if (dupExist()) {
                    Init repo = Utils.readObject(new File(PATH), Init.class);
                    switch (args[0]) {
                    case "init":
                        throw new GitletException();
                    case "add":
                        repo.add(enteredCommand[0]); break;
                    case "commit":
                        if (args.length == 1) {
                            throw new GitletException("Please enter a "
                                    + "commit message.");
                        } else if (args.length > 2) {
                            throw new GitletException("Incorrect operands.");
                        } else {
                            repo.commit(enteredCommand[0]);
                        }
                        break;
                    case "rm":
                        repo.rm(enteredCommand[0]); break;
                    case "log":
                        if (args.length == 1) {
                            repo.logCommits();
                        } else {
                            throw new GitletException("Incorrect operands.");
                        }
                        break;
                    case "global-log":
                        repo.globalLog(); break;
                    case "status":
                        repo.status(); break;
                    case "checkout":
                        int opLen = enteredCommand.length;
                        if (opLen != 1) {
                            repo.checkout(enteredCommand);
                        } else {
                            repo.checkout(enteredCommand[0]);
                        }
                        break;
                    default:
                        throw new GitletException("Incorrect operands.");
                    }
                    Utils.writeObject(new File(PATH), repo);
                } else {
                    initHelperForMain(args);
                }
            }
        } catch (GitletException error) {
            System.exit(0);
        }
    }


    /** Helper for init case in main.
     *
     * @param enteredCommand entered Command
     */
    private static void initHelperForMain(String[] enteredCommand) {
        if (!enteredCommand[0].equals("init")) {
            throw new GitletException("Not in an "
                    + "initialized Gitlet directory");
        } else {
            Utils.writeObject(new File(PATH), new Init());
        }
    }


    /** check if there is duplicate init.
     * @return boolean for if there is
     */
    private static boolean dupExist() {
        String filedDir = System.getProperty("user.dir");
        return new File(filedDir + "/.gitlet").exists();
    }



}
