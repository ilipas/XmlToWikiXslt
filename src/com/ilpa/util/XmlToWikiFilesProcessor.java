package com.ilpa.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.ilpa.jaxb.ObjectFactory;
import com.ilpa.jaxb.Report;

import javax.xml.bind.*;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

/**
 * Processes xml files from the user specified directory, creates a new file
 * containing corresponding wiki markup and saves it in the user specified
 * output directory
 * 
 * @author ilijapasic
 *
 */
public class XmlToWikiFilesProcessor {

	private JAXBContext jaxbContext;
	private Unmarshaller unmarshaller;
	private File outputDirectory;
	private static final String NEW_LINE = "\n";
	private static final String WIKI_FILE_EXTENTION = ".wiki";
	private int headingLevel;

	public XmlToWikiFilesProcessor() {

		headingLevel = 0;

		try {
			jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			unmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void processFiles(File[] filesToProcess) {

		for (File xmlFile : filesToProcess) {
			System.out.println("Processing file: " + xmlFile);
			Report report = null;
			try {
				// Check if xml file
				if (!xmlFile.getName().toLowerCase().endsWith(".xml")) {
					System.out.println("Found non xml file " + xmlFile);
					continue;
				}
				// Unmarshal xml file
				report = (Report) unmarshaller.unmarshal(xmlFile);

				// Process results
				List<Object> reportContent = report.getContent();

				// StringBuilder stringBuilder = processContent(reportContent,
				// new StringBuilder());

				// Create Transformer
				TransformerFactory tf = TransformerFactory.newInstance();
				StreamSource xslt = new StreamSource("src/com/ilpa/util/stylesheet.xsl");
				Transformer transformer = tf.newTransformer(xslt);

				// Source
				JAXBContext jc = JAXBContext.newInstance(Report.class);
				JAXBSource source = new JAXBSource(jc, report);
				
				String filePath = outputDirectory + System.getProperty("file.separator")
				+ FilenameUtils.getBaseName(xmlFile.getName()) + WIKI_FILE_EXTENTION;

				// Result
				//StreamResult result = new StreamResult(new File(filePath));
				
				StreamResult result = new StreamResult(System.out);
				
				

				// Transform
				transformer.transform(source, result);

				// Write result to .wiki file
//				String filePath = outputDirectory + System.getProperty("file.separator")
//						+ FilenameUtils.getBaseName(xmlFile.getName()) + WIKI_FILE_EXTENTION;

				System.out.println("\nWriting file :" + filePath);

				// FileUtils.writeStringToFile(new File(filePath),
				// stringBuilder.toString());

			} catch (JAXBException e) {
				System.out.println("Error processing file : " + xmlFile.getName());
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public File getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

}
