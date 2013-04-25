package tuwien.sbctu.container;

import java.io.Serializable;
import java.net.URI;

import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.MzsConstants.RequestTimeout;
import org.mozartspaces.core.MzsCoreException;

abstract class ContainerXvsm<T extends Serializable> {
	protected final Capi capi;
	protected final URI space;
	protected final String name;

	public ContainerXvsm(final Capi capi, final URI space, final String name) {
		this.capi = capi;
		this.space = space;
		this.name = name;
	}

	protected ContainerReference lookupContainer() throws MzsCoreException {
		return capi.lookupContainer(name, space, RequestTimeout.DEFAULT, null);
	}

	public abstract ContainerReference getContainer();
}
