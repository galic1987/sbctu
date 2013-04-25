package tuwien.sbctu.container;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mozartspaces.capi3.Coordinator;
import org.mozartspaces.capi3.CountNotMetException;
import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants.Container;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.TransactionReference;

public class FifoContainerXvsm<T extends Serializable> extends
		ContainerXvsm<Serializable> {

	private final ContainerReference container;

	public FifoContainerXvsm(final Capi capi, final URI space, final String name)
			throws MzsCoreException {
		super(capi, space, name);

		container = getOrCreateFifoContainer();
	}

	private ContainerReference getOrCreateFifoContainer()
			throws MzsCoreException {
		try {
			return lookupContainer();
		} catch (final MzsCoreException e) {
			final List<Coordinator> obligatoryCoordinators = new ArrayList<>();
			obligatoryCoordinators.add(new FifoCoordinator());

			return capi.createContainer(name, space, Container.UNBOUNDED,
					obligatoryCoordinators, null, null);
		}
	}

	public List<T> readAll() throws MzsCoreException {
		int count = Integer.MAX_VALUE;
		while (count != 0) {
			try {
				return capi.read(container,
						Arrays.asList(FifoCoordinator.newSelector(count)), 0,
						null);
			} catch (final CountNotMetException e) {
				count = e.getCountAvailable();
			}
		}

		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	public List<T> take(final int count, final TransactionReference tx)
			throws MzsCoreException {
		final List<Serializable> entries = capi.take(container,
				Arrays.asList(FifoCoordinator.newSelector(count)), 0, tx);

		return (List<T>) entries;
	}

	public void write(final T element, final TransactionReference tx)
			throws MzsCoreException {
		final Entry entry = new Entry(element,
				FifoCoordinator.newCoordinationData());
		capi.write(container, 0, tx, entry);
	}

	@Override
	public ContainerReference getContainer() {
		return container;
	}
}
