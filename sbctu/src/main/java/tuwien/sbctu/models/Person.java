package tuwien.sbctu.models;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Person implements Serializable, Identifiable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -4886772488952254118L;
	private final Long id;
	private AtomicBoolean busy;
	
	public Person(final Long id){
		this.id = id;
		
	}
	
	public AtomicBoolean getBusy() {
		return busy;
	}

	public void setBusy(AtomicBoolean busy) {
		this.busy = busy;
	}

	public Long getId(){
		return id;
	}
	
	

}
