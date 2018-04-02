/**
 * 
 */
package io.microprofile.showcase.speaker.health;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import io.microprofile.showcase.speaker.rest.ResourceSpeaker;

/**
 * @author jagraj
 *
 */
@Health
@ApplicationScoped
public class SuccessfulHealthCheck implements HealthCheck {
	@Inject
	private ResourceSpeaker resourceSpeaker;
	@Override
	public HealthCheckResponse call() {
		try {
			if(resourceSpeaker.nessProbe().getStatus()==200) {
				return HealthCheckResponse.named("Speaker:successful-check").up().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return HealthCheckResponse.named("Speaker:failed-check").down().build();
	}
}
