/*
 * Copyright 2016 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.blockdetector.internal;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.audio.AudioManager;
import org.terasology.blockdetector.systems.BlockDetectorSystem;
import org.terasology.blockdetector.utilities.DetectorData;
import org.terasology.blockdetector.utilities.LinearAudioDetectorImpl;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.math.Region3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.In;

/**
 * A festive implementation example!
 * <p>
 * Designed to work with internal eggs generated by ShatteredPlanes.
 *
 * @see <a href="https://github.com/Terasology/ShatteredPlanes">ShatteredPlanes source</a>
 */

// Step 1: Register a system extending BaseComponentSystem.
@RegisterSystem
public class EasterEggDetectorSystem extends BaseComponentSystem {
    private static final Logger logger = LoggerFactory.getLogger(EasterEggDetectorSystem.class);

    // Step 2: Inject all the necessary in-game systems.
    // Make sure to inject the blockDetectorSystem!
    @In
    private AudioManager audioManager;

    @In
    private BlockDetectorSystem blockDetectorSystem;

    private DetectorData data;

    // Step 3: Create detector bindings in the initialise() method and add them to the blockDetectorSystem.
    @Override
    public void initialise() {
        Region3i range = Region3i.createFromMinMax(new Vector3i(-40, -40, -40), new Vector3i(40, 40, 40));
        data = new LinearAudioDetectorImpl("BlockDetector:easterEggDetector", Sets.newHashSet("CoreBlocks:Snow"), range, audioManager, "BlockDetector:ScannerBeep", 200, 2000);

        blockDetectorSystem.addDetector(data);
    }

    // Step 4: Gracefully clean up the bindings on shutdown.
    @Override
    public void shutdown() {
        blockDetectorSystem.removeDetector(data.getDetectorUri());
    }
}