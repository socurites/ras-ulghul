package com.socurites.rasulghul.web.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socurites.rasulghul.web.domain.RasUlGhulPrediction;

@Controller
public class RasUlGhulWebController {
	private static final String OPEN_FACE_DIR = "/home/socurites/Runnable/OpenFace/openface/";
	private static final String MODEL_DIR = "/home/socurites/Backup/MachineLearning/artist_faces/model/generated-embeddings-";
	private static final String UPLOAD_DIR = "/home/socurites/Works/melon_works/ras-ulghul/src/main/webapp/resources/upload/";
	
//	private static final String OPEN_FACE_DIR = "/home/ubuntu/OpenFace/openface/";
//	private static final String MODEL_DIR = "/home/ubuntu/artist_faces/model/generated-embeddings-";
//	private static final String UPLOAD_DIR = "/home/ubuntu/git/ras-ulghul/src/main/webapp/resources/upload/";
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "site.index";
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadFile(
			@RequestParam("uploadfile") MultipartFile uploadfile) throws Exception {

		try {
			// Get the filename and build the local file path (be sure that the
			// application have write permissions on such directory)
			String filename = uploadfile.getOriginalFilename();
			String filepath = Paths.get(UPLOAD_DIR, filename).toString();

			// Save the file locally
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(filepath)));
			stream.write(uploadfile.getBytes());
			stream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	} // method uploadFile
	
	
	@RequestMapping(value = "/infer", method = RequestMethod.GET)
	@ResponseBody
	public String infer(@RequestParam("fileName") String fileName, @RequestParam("artist") String artist) throws Exception {
		String command = OPEN_FACE_DIR + "demos/classifier.py infer " + MODEL_DIR + artist + "/classifier.pkl " + UPLOAD_DIR + fileName + " --multi";
		
		
		Process p = Runtime.getRuntime().exec(command);
	    p.waitFor();

	    BufferedReader reader = 
	         new BufferedReader(new InputStreamReader(p.getInputStream()));

	    String line = "";			
	    List<String> lines = new ArrayList<String>();
	    
	    while ((line = reader.readLine())!= null) {
	    	lines.add(line);
	    }

	    List<RasUlGhulPrediction> predictions = new ArrayList<RasUlGhulPrediction>();
	    RasUlGhulPrediction prediction = null;
	    String[] tokens = null;
	    for ( String item : lines ) {
	    	System.out.println(item);
	    	
	    	if ( item.startsWith("Predict") ) {
	    		tokens = item.split(" ");
	    		
	    		prediction = new RasUlGhulPrediction(tokens[1], Float.parseFloat(tokens[5]));
	    		predictions.add(prediction);
	    	}
	    	
	    	if ( item.startsWith("[Warning]") ) {
	    		prediction = new RasUlGhulPrediction("small", 0.0f);
	    		prediction.setErrorMessage(item);
	    		
	    		predictions.add(prediction);
	    		
	    	}
	    }
	    
	    ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(predictions);
	}
}
