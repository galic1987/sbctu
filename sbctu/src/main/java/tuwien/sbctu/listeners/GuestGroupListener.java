package tuwien.sbctu.listeners;

import java.util.EventListener;

public interface GuestGroupListener extends EventListener {
	
	void groupEntering();

	void groupOrders();

	void groupFinish();

	void groupLeavs();
}
