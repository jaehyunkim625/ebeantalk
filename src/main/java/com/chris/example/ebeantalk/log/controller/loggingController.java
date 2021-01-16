package com.chris.example.ebeantalk.log.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//v.2
@RestController
public class loggingController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Environment environment;
	private static String vauleInApplicationYml;

	public String getActiveProfiles() {
		String profileName_s="";
		for (String profileName : environment.getActiveProfiles()) {
			System.out.println("Currently active profile - " + profileName);
			profileName_s = profileName;
		}
		return profileName_s;

	}
	@Value("${test.body}")
	public void setValue(String val) {
		vauleInApplicationYml = val;
	}

	@RequestMapping("/log")
	public ResponseEntity<String> helloWorld() {
//		logger.trace("A TRACE Message!!!");
//		logger.debug("A DEBUG Message!!!");
//		logger.info("An INFO Message!!!");
//		logger.warn("A WARN Message!!!");
//		logger.error("An ERROR Message!!!");

		String message = "Profile: [" + vauleInApplicationYml + "]";

		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@RequestMapping("/log/info")
	public ResponseEntity<String> infolog() {
//		logger.trace("A TRACE Message!!!");
//		logger.debug("A DEBUG Message!!!");
		logger.info("An INFO Message!!!");
//		logger.warn("A WARN Message!!!");
//		logger.error("An ERROR Message!!!");

		String message = "Profile: [" + vauleInApplicationYml + "]";

		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@RequestMapping("/log/warn")
	public ResponseEntity<String> warnlog() {
//		logger.trace("A TRACE Message!!!");
//		logger.debug("A DEBUG Message!!!");
//		logger.info("An INFO Message!!!");
		logger.warn("A WARN Message!!!");
//		logger.error("An ERROR Message!!!");

		String message = "Profile: [" + vauleInApplicationYml + "]";

		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@RequestMapping("/log/error")
	public ResponseEntity<String> errorlog() {
//		logger.trace("A TRACE Message!!!");
//		logger.debug("A DEBUG Message!!!");
//		logger.info("An INFO Message!!!");
//		logger.warn("A WARN Message!!!");
		logger.error("An ERROR Message!!!");

		String message = "Profile: [" + vauleInApplicationYml + "]";

		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@RequestMapping("/log/syserror")
	public ResponseEntity<String> syserrorlog() {
//		logger.trace("A TRACE Message!!!");
//		logger.debug("A DEBUG Message!!!");
//		logger.info("An INFO Message!!!");
//		logger.warn("A WARN Message!!!");
//		logger.error("An ERROR Message!!!");

		System.err.println ("[System error print]*******");

		return new ResponseEntity<>("[System error print]*******", HttpStatus.OK);
	}

	@RequestMapping("/log/ls")
	public ResponseEntity<String> showFileList() {
		StringBuilder returnVal = new StringBuilder();
		returnVal.append("<h1>[ls -alF Result]</h1>" + "<br>");
		String s;
		Process p;
		try {
			p = Runtime.getRuntime().exec("ls -alhF");
			BufferedReader br = new BufferedReader(
					new InputStreamReader(p.getInputStream()));
			while ((s = br.readLine()) != null) {
				System.out.println("line: " + s);
				returnVal.append(s).append("<br>");
			}
			p.waitFor();
			System.out.println ("exit: " + p.exitValue());
			p.destroy();
		} catch (Exception e) {
			System.err.println ("exit: " + "Got Exception!");
			System.err.println (e.getCause().toString());
		}

		return new ResponseEntity<>(returnVal.toString(), HttpStatus.OK);
	}

	@RequestMapping("/log/ps")
	public ResponseEntity<String> showLiveProcess() {
		StringBuilder returnVal = new StringBuilder();
		returnVal.append("<h1>[ps -few]</h1>" + "<br>");
		String s;
		try {
			String process;
			// getRuntime: Returns the runtime object associated with the current Java application.
			// exec: Executes the specified string command in a separate process.
			Process p = Runtime.getRuntime().exec("ps -few");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((process = input.readLine()) != null) {
				System.out.println(process);
				returnVal.append(process).append("<br>");
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}

		return new ResponseEntity<>(returnVal.toString(), HttpStatus.OK);
	}

	@RequestMapping("/log/awslogs")
	public ResponseEntity<String> showAwslogs() {
		StringBuilder returnVal = new StringBuilder();
		returnVal.append("<h1>[cat /var/log/awslogs.log]</h1>" + "<br>");
		String s;
		Process p;
		try {
			p = Runtime.getRuntime().exec("cat /var/log/awslogs.log");
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((s = br.readLine()) != null) {
				System.out.println("line: " + s);
				returnVal.append(s).append("<br>");
			}
			p.waitFor();
			System.out.println ("exit: " + p.exitValue());
			p.destroy();
		}  catch (Exception err) {
			err.printStackTrace();
		}

		return new ResponseEntity<>(returnVal.toString(), HttpStatus.OK);
	}
}
