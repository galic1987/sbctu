package tuwien.sbctu.handlers;

import java.io.IOException;

public class ShutdownListener implements Runnable {
	private final ShutdownTarget target;

	public ShutdownListener(final ShutdownTarget target) {
		this.target = target;
	}

	@Override
	public void run() {
		System.out.println("Press enter to stop.");
		try {
			System.in.read();
		} catch (final IOException e) {
		}
		System.out.println("Shutting down.");
		target.shutdown();
	}
}
