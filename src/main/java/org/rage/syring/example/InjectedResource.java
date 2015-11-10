package org.rage.syring.example;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.rage.syring.annotation.ApplicationProperty;

/**
 * @author hector.mendoza
 *
 */
@Stateless
@ApplicationPath("rest")
@Path("/inject")
public class InjectedResource extends Application {
	@Inject
	@ApplicationProperty(name = "properties.file.location", type = ApplicationProperty.Types.SYSTEM)
	private String systemInjectedString;

	@Inject
	@ApplicationProperty(name = "example.property", type = ApplicationProperty.Types.FILE)
	private String fileInjectedString;

	// This attribute is going to be injected thru initializer method injection (supported too)
	private String inMethod;

	/**
	 * Represents setInMethod
	 *
	 * @param arg
	 * @since Sep 1, 2015
	 *
	 */
	@Inject
	public void setInMethod(@ApplicationProperty(name = "example.property", type = ApplicationProperty.Types.FILE)
		final String arg) {
		inMethod = arg;
	}

	/**
	 * Represents getInjectedString
	 *
	 * @return Response
	 * @since Aug 26, 2015
	 *
	 */
	@GET
	@Produces(value = "application/json")
	public Response getInjectedString() {
		return Response.status(Response.Status.OK).entity(toString()).build();
	}

	/**
	 * Overrides toString
	 *
	 * @return String
	 * @since Aug 26, 2015
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("From File: %s\nFrom System: %s\nFrom method Injection: %s\n", fileInjectedString,
				systemInjectedString, inMethod);
	}

	/**
	 * Overrides getClasses
	 *
	 * @return Set <Class <?>>
	 * @since Aug 26, 2015
	 * @see javax.ws.rs.core.Application#getClasses()
	 */
	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> resources = new HashSet<Class<?>>();
		resources.add(this.getClass());
		return resources;
	}
}
