package tuwien.sbctu.handlers;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.mozartspaces.core.MzsCoreException;

import tuwien.sbctu.Pizzeria;

//import cps.service.ProductionService;
//import cps.service.xvsm.ProductionServiceXvsm;

public abstract class Worker implements Runnable, ShutdownTarget {
	protected Pizzeria production;
	protected final Long id;
	private boolean work = true;

	private final ExecutorService jobExecutor;
	private final AtomicBoolean busy = new AtomicBoolean(false);

	public Worker() throws MzsCoreException, InterruptedException, IOException {
		production = new Pizzeria();
		this.id = new Long(32);//production.hashCode();

		final ExecutorService shutdownExecutor = Executors
				.newSingleThreadExecutor();
		shutdownExecutor.execute(new ShutdownListener(this));
		shutdownExecutor.shutdown();

		jobExecutor = Executors.newSingleThreadExecutor();
	}

	@Override
	public void run() {
		registerListeners();
		work();

		while (work) {
			try {
				Thread.sleep(TimeUnit.SECONDS.toMillis(1));
			} catch (final InterruptedException e) {
			}
		}

		unregisterListeners();
	}

	protected void work() {
		synchronized (busy) {
			if (busy.get()) {
				return;
			}

			jobExecutor.execute(new Runnable() {
				@Override
				public void run() {
					synchronized (busy) {
						busy.set(true);
					}

					try {
						while (executeTask()) {
						}
					} catch (final Exception e) {
					}

					synchronized (busy) {
						busy.set(false);
					}
				}
			});
		}
	}

	protected abstract boolean executeTask();

	protected abstract void registerListeners();

	protected abstract void unregisterListeners();

	@Override
	public void shutdown() {
		work = false;
	}
}
