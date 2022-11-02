package org.opengame.cosmos.physics;

import lombok.extern.java.Log;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.opengame.cosmos.object.Planet;
import org.opengame.engine.object.SceneObject;

import java.util.ArrayList;
import java.util.List;

@Log
public class PhysicsManager extends SceneObject {
    private static final double G = 6.67 / 100000000000.0;

    private List<Planet> spheres;

    public PhysicsManager() {
        spheres = new ArrayList<>(10);
    }

    public void add(Planet sphere) {
        spheres.add(sphere);
    }

    @Override
    public void update(float time, float tickTime) {
        for (int i = 0; i < spheres.size(); i++) {
            var originSphere = spheres.get(i);
            var resultForceVector = new Vector3d();
            for (int j = 0; j < spheres.size(); j++) {
                if (i == j) continue;
                var anotherSphere = spheres.get(j);
                var force = calculateGravityForce(originSphere, anotherSphere);
                resultForceVector.add(force);
            }

            var acceleration = resultForceVector.div(originSphere.getMass());
            originSphere.setAcceleration(acceleration);
        }
    }

    private Vector3f calculateGravityForce(Planet originSphere, Planet anotherSphere) {
        var forceVector = new Vector3f(
                anotherSphere.getPosition().x - originSphere.getPosition().x,
                anotherSphere.getPosition().y - originSphere.getPosition().y,
                anotherSphere.getPosition().z - originSphere.getPosition().z
        );

        var squareDistance = originSphere.getPosition().distanceSquared(anotherSphere.getPosition());
        var forceValue = G * ((originSphere.getMass() * anotherSphere.getMass()) / squareDistance);

        return forceVector.mul((float) forceValue);
    }
}
