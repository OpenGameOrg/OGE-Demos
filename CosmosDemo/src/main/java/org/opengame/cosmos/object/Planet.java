package org.opengame.cosmos.object;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.opengame.engine.Engine;
import org.opengame.engine.object.LineStrip;
import org.opengame.engine.scene.Mesh;
import org.opengame.engine.scene.MeshLoader;
import org.opengame.engine.scene.Model;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@Getter
@Setter
@Log
public class Planet extends Model {
    private static final String SPHERE_MODEL_NAME = "models/sphere-smooth.obj";
    public static final double EARTH_MASS = 59722.0 * 10000000000.0 * 10000000000.0;
    private static final float TO_KM_COEFF = 6371;

    private float mass;
    private Vector3d velocity;
    private Vector3d acceleration;

    private boolean showTrajectory = false;
    private LineStrip trajectory;

    public Planet() throws IOException {
        super(MeshLoader.loadMeshes(Engine.getWorkingDirectory() + SPHERE_MODEL_NAME));

        mass = 1;
        velocity = new Vector3d();
        acceleration = new Vector3d();
    }

    public double getMassKG() {
        return mass * EARTH_MASS;
    }

    float temp = 0;

    @Override
    public void update(float time, float tickTime) {
        getPosition().add(new Vector3f((float) velocity.x, (float) velocity.y, (float) velocity.z).mul(tickTime * 1));
        velocity.add(acceleration.mul(tickTime * 1));

        setOrientation(getOrientation().rotateAxis(tickTime / 100 *  0.0157f, new Vector3f(0, 1, 0)));
        temp++;

        if (mass == 1 && temp > 111) {
            log.info("Earth velocity x: " + (velocity.x * TO_KM_COEFF) + " km/s");
            log.info("Earth velocity z: " + (velocity.z * TO_KM_COEFF) + " km/s");
            temp = 0;

            if (!showTrajectory) {
                return;
            }

            updateTrajectory();
        }
    }

    private void updateTrajectory() {
        try {
            if (trajectory == null) {
                var trajectoryPoints = new ArrayList<Vector3f>();
                trajectoryPoints.add(new Vector3f(getPosition().x, getPosition().y, getPosition().z));
                trajectory = new LineStrip(trajectoryPoints, Color.cyan, 222);
                Engine.getCurrentScene().add(trajectory);

                return;
            }

            trajectory.addPoint(new Vector3f(getPosition().x, getPosition().y, getPosition().z));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setShowTrajectory(boolean isShow) {
        this.showTrajectory = isShow;
    }
}
