package ttt.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import ttt.model.Gamedata;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class gamePlaying {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	List<String> playstatus;
	/*
	 * 
	 * List<Integer> i; List<Integer> j; List<Boolean> isX; List<Boolean> isO;
	 */
	Queue<DeferredResult<String>> results;

	public gamePlaying() {
		playstatus = new ArrayList<String>();

		results = new LinkedList<DeferredResult<String>>();
	}

	public List<String> getPlaystatus() {
		return playstatus;
	}

	public Queue<DeferredResult<String>> getResults() {
		return results;
	}

	public void add(String username) {
		playstatus.add(username);
		logger.debug(username + " added.");
		processDeferredResults();
	}

	public void remove(String username) {
		playstatus.remove(username);
		logger.debug(username + " removed.");
		processDeferredResults();
	}

	public void fullclear() {
		
		playstatus.clear();
	}
	
	public void addResult(DeferredResult<String> result) {
		results.add(result);
	}

	public static ObjectMapper getObjectmapper() {
		return objectMapper;
	}

	public static Logger getLogger() {
		return logger;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(joinGameService.class);

	private void processDeferredResults() {
		// convert username list to json
		String json = "";
		try {
			StringWriter sw = new StringWriter();
			objectMapper.writeValue(sw, playstatus);

			json = sw.toString();
		} catch (Exception e) {
			logger.error("Failed to write to JSON", e);
		}

		// process queued results
		/*
		 * while (!results.isEmpty()) { DeferredResult<String> result =
		 * results.remove(); result.setResult(json); }
		 */
		for (DeferredResult<String> result : results) {
			result.setResult(json);
		}

		results.clear();
	}

}
