package com.cloudbees.literate.cli;


import org.cloudbees.literate.api.v1.ProjectModel;
import org.cloudbees.literate.api.v1.ProjectModelBuildingException;
import org.cloudbees.literate.api.v1.ProjectModelFormatter;
import org.cloudbees.literate.api.v1.ProjectModelRequest;
import org.cloudbees.literate.api.v1.ProjectModelSource;
import org.cloudbees.literate.api.v1.vfs.FilesystemRepository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author stephenc
 * @since 23/05/2013 12:34
 */
public class Main {

    public static void main(String... args) {
        if (args.length == 0 || args[0].contains("help")) {
            handleHelp();
            return;
        }
        handleCommand(args);
    }


    private static void handleHelp() {
        System.err.println(
                "Usage: \n\nThis command will look for a .cloudbees.md with content, "
                        + "or a .cloudbees.md and a README.md in specified directory."
                        +
                        "\nCheck syntax of literate build in a specified directory\n\tliterate .\n" +
                        "\nReturn the build command for the specified environment\n\tliterate <dir> <environment "
                        + "name>\n"
                        +
                        "\nReturn the build command for the first build section found\n\tliterate <dir> build\n\n" +
                        "\n\nFor example, to run the build command for a literate project in the current directory:\n" +
                        "\n\tliterate . build | sh");
    }


    private static void handleCommand(String[] args) {
        File dir = args.length == 0 ? new File(".") : new File(args[0]);
        FilesystemRepository repository = new FilesystemRepository(dir);
        System.err.println("Parsing project in " + dir.getAbsolutePath());
        try {
            ProjectModel model = new ProjectModelSource(Main.class.getClassLoader())
                    .submit(ProjectModelRequest.builder(repository).build());
            if (args.length > 1) {
                for (String command : buildSection(model, args)) {
                    System.out.println(command);
                }
            } else {
                System.err.println("Model successfully parsed");
                System.out.println(ProjectModelFormatter.getInstance(ProjectModelFormatter.MARKDOWN).format(model));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (ProjectModelBuildingException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static List<String> buildSection(ProjectModel model, String[] args) {
        if (args.length == 2 && args[1].equals("build")) {
            return model.getBuild().getCommands().values().iterator().next();
        } else {
            return model.getBuildFor(Arrays.copyOfRange(args, 1, args.length));
        }

    }
}
