
	
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
	public class playGameService {

		private static final ObjectMapper objectMapper = new ObjectMapper();
		List<String> hostplayer;
		List<String> joinplayer;
		List<String> player;
		
		Queue<DeferredResult<String>> results;

		public playGameService() {
			hostplayer = new ArrayList<String>();
			joinplayer = new ArrayList<String>();
			player = new ArrayList<String>();
			results = new LinkedList<DeferredResult<String>>();
		}

		public List<String> getHostplayer() {
			return hostplayer;
		}
		public List<String> getJoinplayer() {
			return joinplayer;
		}
		public List<String> getPlayer() {
			return player;
		}
		
		public void fullclear() {
			
			player.clear();
			hostplayer.clear();
			joinplayer.clear();
		}

		
		public Queue<DeferredResult<String>> getResults() {
			return results;
		}
		public void addHost(String username) {
			hostplayer.add(username);
			logger.debug(username + " added.");
			processDeferredResults();
		}
		public void addJoin(String username) {
			joinplayer.add(username);
			logger.debug(username + " added.");
			processDeferredResults();
		}
		public void addPlayer(String username) {
			player.add(username);
			logger.debug(username + " added.");
			processDeferredResults();
		}
		
		public void removeHost(String username) {
			hostplayer.remove(username);
			logger.debug(username + " removed.");
			processDeferredResults();
		}public void removeJoin(String username) {
			joinplayer.remove(username);
			logger.debug(username + " removed.");
			processDeferredResults();
		}public void removePlayer(String username) {
			player.remove(username);
			logger.debug(username + " removed.");
			processDeferredResults();
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
				objectMapper.writeValue(sw, hostplayer);
				objectMapper.writeValue(sw, joinplayer);
				objectMapper.writeValue(sw, player);
				
				json = sw.toString();
			} catch (Exception e) {
				logger.error("Failed to write to JSON", e);
			}

			// process queued results
		/*	while (!results.isEmpty()) {
				DeferredResult<String> result = results.remove();
				result.setResult(json);
			}*/
			  for( DeferredResult<String> result : results )
		        {
		            result.setResult( json );
		        }
		        
		results.clear();
		}


	}
