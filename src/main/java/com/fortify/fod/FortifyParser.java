package com.fortify.fod;

import org.apache.commons.cli.*;

import java.util.Comparator;

/**
 * Created by petebeegle on 9/20/2016.
 */
public class FortifyParser {
    private Options options = new Options();
    private CommandLineParser parser = new DefaultParser();
    private CommandLine cmd = null;

    public FortifyParser() {
        Option help = new       Option("help", "print this message");
        Option version = new    Option("version", "print the version information and exit");

        Option username = Option.builder("u")
                .hasArg(true)
                .required(true)
                .longOpt("username")
                .argName("user")
                .desc("username/api key")
                .build();

        Option password = Option.builder("p")
                .hasArg(true)
                .required(true)
                .longOpt("password")
                .argName("pass")
                .desc("password/api secret")
                .build();

        Option bsiUrl = Option.builder("url")
                .hasArg(true)
                .required(true)
                .longOpt("bsiUrl")
                .argName("url")
                .desc("build server url")
                .build();

        Option zipLocation = Option.builder("loc")
                .hasArg(true)
                .required(true)
                .longOpt("zipLocation")
                .argName("file")
                .desc("location of scan")
                .build();

        options.addOption(help);
        options.addOption(version);
        options.addOption(username);
        options.addOption(password);
        options.addOption(bsiUrl);
        options.addOption(zipLocation);
    }

    public void parse(String[] args) {
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("help")) {
                help();
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.out.println();
            help();
        }
    }

    public void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.setOptionComparator(HelpComparator);

        formatter.printHelp( "FodUpload-5.3.jar", options, true );
    }

    public Options getOptions() {
        return options;
    }

    /*
     * Compares options so that they are ordered:
     * 1.) by required, then by
     * 2.) short operator
     * Used for sorting the results of the Help command.
     */
    private static Comparator<Option> HelpComparator = new Comparator<Option>() {
        @Override
        public int compare(Option o1, Option o2) {
            String required1 = o1.isRequired() ? "1" : "0";
            String required2 = o2.isRequired() ? "1" : "0";

            int result = required2.compareTo(required1);
            if (result == 0) {
                result = o1.getOpt().compareToIgnoreCase(o2.getOpt());
            }
            return result;
        }
    };
}
