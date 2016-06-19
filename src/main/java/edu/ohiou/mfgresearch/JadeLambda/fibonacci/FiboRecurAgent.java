package edu.ohiou.mfgresearch.JadeLambda.fibonacci;

import jade.core.Agent;
import jade.util.Logger;

public class FiboRecurAgent extends Agent {
	
	private static final Logger logger = Logger.getJADELogger(FiboRecurAgent.class.getName());

	/**
	 * Serialization for apparently no use, this agent will nevr be serialized
	 */
	private static final long serialVersionUID = 3445922635591766683L;


	@Override
	protected void setup() {
		super.setup();
		
		logger.info(() -> {
				return "Hello "+ this.getArguments()[0].toString() + "! This is FiboRecurAgent!"; 
			});
		
	}
	
	

}
