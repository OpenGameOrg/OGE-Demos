package org.opengame.cosmos;

import org.joml.Vector3d;
import org.joml.Vector3f;
import org.opengame.cosmos.object.Planet;
import org.opengame.cosmos.physics.PhysicsManager;
import org.opengame.engine.Engine;
import org.opengame.engine.app.AppConfig;
import org.opengame.engine.camera.FlyingCamera;
import org.opengame.engine.object.Line;
import org.opengame.engine.object.LineStrip;
import org.opengame.engine.scene.MeshLoader;
import org.opengame.engine.scene.Scene;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
    private static final float PLANET_SCALE = 111;
    public static void main(String[] args) throws IOException, URISyntaxException {
        var engine = new Engine();
        var config = new AppConfig();
        config.setAppName("CosmosDemo");
        config.setAppVersion("1.0.0");
        if (args.length == 0) {
            config.setWorkingDirectory(Main.class.getResource(".").getPath() + "../../../../../");
        } else {
            config.setWorkingDirectory(args[0]);
        }
        engine.Init(config);

        var scene = new Scene();
        var camera = (FlyingCamera) scene.getCamera();
        camera.setFlySpeed(5f);
        //camera.setDebugMode(true);
        camera.setPerspective(35, Engine.getScreenWidth(), Engine.getScreenHeight(), 0.1f, 1111500f);

        var sun = new Planet();
        sun.setMass(333000);
        sun.setScale(new Vector3f(109, 109, 109));
        sun.setTexture("textures/sun.dds");

        var mercury = new Planet();
        mercury.setScale(new Vector3f(0.3829f, 0.3829f, 0.3829f));
        mercury.setPosition(new Vector3f(9093.759f, 0, 0));
        mercury.setVelocity(new Vector3d(0.0f, 0, 0.005f));
        mercury.setTexture("textures/mercury.dds");
        mercury.setShowTrajectory(true);

        var venus = new Planet();
        venus.setScale(new Vector3f(0.815f, 0.815f, 0.815f));
        venus.setPosition(new Vector3f(16283.205f, 0, 0));
        venus.setTexture("textures/venus.dds");

        var earth = new Planet();
        earth.setPosition(new Vector3f(23680, 0, 0));
        //earth.setVelocity(new Vector3d(0, 0, 0.003f));
        earth.setTexture("textures/earth.dds");
        //earth.setShowTrajectory(true);

        var mars = new Planet();
        mars.setScale(new Vector3f(0.532f, 0.532f, 0.532f));
        mars.setPosition(new Vector3f(36101.08f, 0, 0));
        mars.setTexture("textures/mars.dds");

        var jupiter = new Planet();
        jupiter.setScale(new Vector3f(11.2f, 11.2f, 11.2f));
        jupiter.setPosition(new Vector3f(122115.837f, 0, 0));
        jupiter.setVelocity(new Vector3d(0.0f, 0, 0.0025f));
        jupiter.setTexture("textures/jupiter.dds");
        jupiter.setShowTrajectory(true);

        var saturn = new Planet();
        saturn.setScale(new Vector3f(9.464f, 9.464f, 9.464f));
        saturn.setPosition(new Vector3f(219745.72f, 0, 0));
        saturn.setTexture("textures/saturn.dds");

        var uranus = new Planet();
        uranus.setScale(new Vector3f(3.981f, 3.981f, 3.981f));
        uranus.setPosition(new Vector3f(439491.45f, 0, 0));
        uranus.setTexture("textures/uranus.dds");

        var neptune = new Planet();
        neptune.setScale(new Vector3f(3.8869f, 3.8869f, 3.8869f));
        neptune.setPosition(new Vector3f(714173.5991f, 0, 0));
        neptune.setTexture("textures/neptune.dds");

        var sphere = MeshLoader.loadModel("models/cylinder.obj");
        sphere.setPosition(new Vector3f(10, 0, 0));
        sphere.setScale(new Vector3f(1, 5f, 5f));
        sphere.setTexture("textures/test.dds");

        //camera.setPosition(new Vector3f(sun.getPosition().x, 50000, 0));
        camera.setPosition(new Vector3f(earth.getPosition().x, earth.getPosition().y, earth.getPosition().z - 155));
        //camera.setPosition(new Vector3f(earth.getPosition().x, earth.getPosition().y, earth.getPosition().z - 1));
        camera.setRotation(new Vector3f(0, 0, 0));

        mercury.setScale(mercury.getScale().mul(PLANET_SCALE));
        venus.setScale(venus.getScale().mul(PLANET_SCALE));
        earth.setScale(earth.getScale().mul(PLANET_SCALE));
        mars.setScale(mars.getScale().mul(PLANET_SCALE));
        jupiter.setScale(jupiter.getScale().mul(PLANET_SCALE));
        saturn.setScale(saturn.getScale().mul(PLANET_SCALE));
        uranus.setScale(uranus.getScale().mul(PLANET_SCALE));
        neptune.setScale(neptune.getScale().mul(PLANET_SCALE));

//        var testLine = new LineStrip(List.of(earth.getPosition(), new Vector3f(), new Vector3f(), new Vector3f(23680, 0, 50)), Color.cyan);
//        scene.add(testLine);

        scene.add(sun);
//        scene.add(mercury);
        //scene.add(venus);
        scene.add(earth);
//        scene.add(mars);
//        scene.add(jupiter);
//        scene.add(saturn);
//        scene.add(uranus);
//        scene.add(neptune);
        scene.add(sphere);

        engine.setCurrentScene(scene);

        var physics = new PhysicsManager();
        physics.add(sun);
        //physics.add(earth);
//        physics.add(mercury);
//        physics.add(jupiter);
        scene.add(physics);

        engine.startLoop();
    }
}
