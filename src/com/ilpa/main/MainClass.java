package com.ilpa.main;

import java.io.File;
import java.io.FilenameFilter;

import com.ilpa.util.DirectoryPathWatcher;
import com.ilpa.util.XmlToWikiFilesProcessor;

public class MainClass {

	public static void main(String[] args) {

		String inputDir = "input";
		String outputDir = "output";

		if (args.length < 2) {
			System.err.println("Expected names/paths of the input and output directories to be specified, but found " + args.length
					+ " arguments. Using defautl values: input, output");
		} else {
			// Get user specified input and output directories
			inputDir = args[0];
			outputDir = args[1];
		}

		
		final File inputDirectory = new File(inputDir);
		if (!inputDirectory.exists()) {
			createDirectory(inputDirectory);
		}

		final File outputDirectory = new File(outputDir);
		if (!outputDirectory.exists()) {
			createDirectory(outputDirectory);
		}

		// Get existing xml files from the specified input directory
		File[] xmlFiles = inputDirectory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".xml");
			}
		});
		
		

		XmlToWikiFilesProcessor xmlToWikiFilesProcessor = new XmlToWikiFilesProcessor();
		xmlToWikiFilesProcessor.setOutputDirectory(outputDirectory);

		// Process existing files if any
		if (xmlFiles.length != 0) {
			System.out.println("Found " + xmlFiles.length + "files. Processing...");
			xmlToWikiFilesProcessor.processFiles(xmlFiles);
		}

		// Keep looking for new files that might be added to the input
		// directory
		DirectoryPathWatcher.watchDirectoryPath(inputDirectory, xmlToWikiFilesProcessor);
		System.out.println("Watching for new files in:  " + inputDirectory);

	}

	private static void createDirectory(File directory) {
		System.out.println(directory + " directory doesn't exists. Creating new...");
		boolean isNewOutputDirectoryCreated = directory.mkdir();
		if (isNewOutputDirectoryCreated) {
			System.out.println("Directory created.");
		} else {
			System.err.println("Failed to create directory " + directory);
			System.err.println("Exiting...");
			System.exit(1);
		}
		
	}

}
