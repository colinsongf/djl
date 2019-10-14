/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package ai.djl.training.initializer;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;

/**
 * Initializes weights with random values sampled from a normal distribution with a mean of zero and
 * standard deviation of {@code sigma}. Default standard deviation is 0.01.
 */
public class NormalInitializer implements Initializer {

    private double sigma;

    public NormalInitializer() {
        this.sigma = 0.01;
    }

    /**
     * Initialize Normal initializer.
     *
     * @param sigma float, Standard deviation of the normal distribution.
     */
    public NormalInitializer(double sigma) {
        this.sigma = sigma;
    }

    /** {@inheritDoc} */
    @Override
    public NDArray initialize(NDManager manager, Shape shape, DataType dataType) {
        return manager.randomNormal(0.0, sigma, shape, dataType, manager.getDevice());
    }
}
