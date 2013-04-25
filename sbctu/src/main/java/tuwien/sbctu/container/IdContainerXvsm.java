package tuwien.sbctu.container;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.mozartspaces.capi3.ContainerNotFoundException;
import org.mozartspaces.capi3.KeyCoordinator;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants.Container;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.TransactionReference;

public final class IdContainerXvsm extends ContainerXvsm<Long> {
	public static final String KEY_WORKER = "worker";
	public static final String KEY_PART = "part";
	public static final String KEY_JOB = "job";

	private ContainerReference container;

	public IdContainerXvsm(final Capi capi, final URI space, final String name)
			throws MzsCoreException {
		super(capi, space, name);

		container = getOrCreateIdContainer();
	}

	private ContainerReference getOrCreateIdContainer() throws MzsCoreException {
		try {
			return lookupContainer();
		} catch (final ContainerNotFoundException e) {
			final List<KeyCoordinator> obligatoryCoordinators = Arrays
					.asList(new KeyCoordinator());

			container = capi.createContainer(name, space, Container.UNBOUNDED,
					obligatoryCoordinators, null, null);

//			write(null, KEY_WORKER, 0L);
//			write(null, KEY_PART, 0L);
//			write(null, KEY_JOB, 0L);

			return container;
		}
	}

	public Long getNextId(final String key) throws MzsCoreException {
		final TransactionReference tx = capi.createTransaction(
				TimeUnit.SECONDS.toMillis(3), space);

		try {
			Long id = take(tx, key);
			id += 1;
			write(tx, key, id);
			capi.commitTransaction(tx);

			return id;
		} catch (final MzsCoreException e) {
			capi.rollbackTransaction(tx);
			throw e;
		}
	}

	private Long take(final TransactionReference tx, final String key)
			throws MzsCoreException {
		final ArrayList<Serializable> entries = capi.take(container,
				Arrays.asList(KeyCoordinator.newSelector(key)), 0, tx);
		final Long id = (Long) entries.get(0);
		return id;
	}

	private void write(final TransactionReference tx, final String key,
			final Long id) throws MzsCoreException {
		final Entry idEntry = new Entry(id, Arrays.asList(KeyCoordinator
				.newCoordinationData(key)));

		capi.write(container, 0, tx, idEntry);
	}

	@Override
	public ContainerReference getContainer() {
		return container;
	}
}
