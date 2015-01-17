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

import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class oPosition {
	List<Integer> oposition;

	private static final ObjectMapper objectMapper = new ObjectMapper();

	Queue<DeferredResult<String>> results;

	public oPosition() {
		oposition = new ArrayList<Integer>();
		results = new LinkedList<DeferredResult<String>>();
	}

	public List<Integer> getUsernames() {
		return oposition;
	}

	public void add(Integer position) {
		oposition.add(position);
		logger.debug(position + " added.");
		processDeferredResults();
	}

	public void remove(Integer position) {
		oposition.remove(position);
		logger.debug(position + " removed.");
		processDeferredResults();
	}

	public void addResult(DeferredResult<String> result) {
		results.add(result);
	}

	public static ObjectMapper getObjectmapper() {
		return objectMapper;
	}

	public void fullclear() {
		
		oposition.clear();
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
			List<String> newList = new ArrayList<String>(oposition.size());
					for (Integer myInt : oposition) { 
					  newList.add(String.valueOf(myInt)); 
					}
		
			objectMapper.writeValue(sw, newList);
			json = sw.toString();
		} catch (Exception e) {
			logger.error("Failed to write to JSON", e);
		}

		// process queued results
		while (!results.isEmpty()) {
			DeferredResult<String> result = results.remove();
			result.setResult(json);
		}
	}

}
