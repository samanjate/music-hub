package edu.neu.cs5200.aws;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * This class connects to our S3 bucket on AWS and uploads all our audio files
 * into respective folder on S3 bucket
 *
 * @author sreematta
 */

public class S3Connection {
	
	private static final String SUFFIX = "/";
	public static final String AWS_BUCKET_URL = "https://s3.amazonaws.com/music-hub-app/";
	private static final String FILE_SEPERATOR = "\\";

	private static final String ACCESSKEYID = System.getenv("ACCESS_KEY_ID");
	private static final String SECRETACCESSKEY = System.getenv("SECRET_ACCESS_KEY");
	private static final String TEMP_DIR = Paths.get(System.getProperty("user.dir"), "temp").toString();
	private static final String BUCKET_NAME = "music-hub-app";
	private static AWSCredentials credentials = new BasicAWSCredentials(ACCESSKEYID, SECRETACCESSKEY);
	private static AmazonS3 s3client = new AmazonS3Client(credentials);
	private static S3Connection s3Connection;
	private static final Logger LOGGER = Logger.getLogger(S3Connection.class.getName());

	private final Path rootLocation = Paths.get("temp");

	/**
	 * Create private constructor
	 */
	private S3Connection() {
	}

	/**
	 * Create a static method to get instance.
	 */
	public static S3Connection getInstance() {
		if (s3Connection == null) {
			s3Connection = new S3Connection();
		}
		return s3Connection;
	}

	/**
	 * upload files into S3 bucket
	 * 
	 * @param folderName
	 * @param file
	 * @throws IOException
	 * @throws IllegalStateException
	 * @throws AmazonClientException
	 * @throws AmazonServiceException
	 */
	public void uploadFilesInFolder(String folderName, MultipartFile file) throws IOException {
		// upload file to folder and set it to public
		String fileName = folderName + SUFFIX + file.getOriginalFilename();
		Files.copy(file.getInputStream(), this.rootLocation.resolve("temp"));
		File f = multipartToFile(file);
		s3client.putObject(
				new PutObjectRequest(BUCKET_NAME, fileName, f).withCannedAcl(CannedAccessControlList.Private));
		clearTempFiles();
	}

	public static File multipartToFile(MultipartFile multipart) throws IOException {
		File convFile = new File(TEMP_DIR + FILE_SEPERATOR + multipart.getOriginalFilename());
		multipart.transferTo(convFile);
		return convFile;
	}

	/**
	 * This fetches the S3 object for the key specified and returns it as a File
	 * Object
	 *
	 * @param key
	 *            The key of the object required
	 * @return File Object of the requested resource
	 */
	public static File getFileFromBucket(String key) {
		File file = new File(TEMP_DIR + SUFFIX + key);
		if (file.mkdirs())
			LOGGER.log(Level.INFO, "Created Temporary Directory");
		S3Object requestedObject = s3client.getObject(BUCKET_NAME, key);
		InputStream in = requestedObject.getObjectContent();
		try {
			Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			in.close();
			return file;
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString());
			return null;
		}

	}

	/**
	 * This method first deletes all the files in given folder and than the folder
	 * itself
	 * 
	 * @param folderName
	 */
	public static void deleteFolder(String folderName) {
		AmazonS3 client = S3Connection.s3client;

		List<S3ObjectSummary> fileList = client.listObjects(BUCKET_NAME, folderName).getObjectSummaries();
		for (S3ObjectSummary file : fileList) {
			client.deleteObject(BUCKET_NAME, file.getKey());
		}
		client.deleteObject(BUCKET_NAME, folderName);
	}

	/**
	 * clears temp files from local temp directory which are created while triggered
	 * business logic is ran
	 * 
	 * @return true iff all the files in the directory are deleted
	 * @throws IOException
	 */
	public static void clearTempFiles() throws IOException {
		FileUtils.cleanDirectory(new File(TEMP_DIR));
	}

}
