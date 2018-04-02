package io.microprofile.showcase.speaker.health;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import io.microprofile.showcase.speaker.rest.ResourceSpeaker;


/**
 * 
 * @author jagraj
 *
 */
@Health
@ApplicationScoped
public class FailedHealthCheck implements HealthCheck{
	
	@Inject
	private ResourceSpeaker resourceSpeaker;
	@Inject 
	@ConfigProperty(name="isAppDown") Optional<String> isAppDown;
	private @Inject HealthCheckBean healthCheckBean;

    @Override
    public HealthCheckResponse call() {
		try {
			if(resourceSpeaker.nessProbe().getStatus()!=200 || ((isAppDown.isPresent()) && (isAppDown.get().equals("true")))) {
				return HealthCheckResponse.named("Speaker:failed-check").down().build();
			}
			else if(healthCheckBean.getIsAppDown()!=null && healthCheckBean.getIsAppDown().booleanValue()==true) {
				return HealthCheckResponse.named("Speaker:failed-check").down().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return HealthCheckResponse.named("Speaker:successful-check").up().build();
    }


}
