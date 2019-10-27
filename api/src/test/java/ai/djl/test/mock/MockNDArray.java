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
package ai.djl.test.mock;

import ai.djl.Device;
import ai.djl.ndarray.Matrix;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.index.NDIndex;
import ai.djl.ndarray.index.NDIndexFixed;
import ai.djl.ndarray.internal.NDArrayEx;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;
import ai.djl.ndarray.types.SparseFormat;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.UUID;
import java.util.function.Predicate;

public class MockNDArray implements NDArray {

    private Device device;
    private String uid;
    private SparseFormat sparseFormat;
    private DataType dataType;
    private Shape shape;
    private NDManager manager;
    private ByteBuffer data;

    public MockNDArray() {}

    public MockNDArray(
            NDManager manager,
            Device device,
            Shape shape,
            DataType dataType,
            SparseFormat sparseFormat) {
        this.manager = manager;
        this.device = device;
        this.shape = shape;
        this.dataType = dataType;
        this.sparseFormat = sparseFormat;
        uid = UUID.randomUUID().toString();
    }

    @Override
    public NDManager getManager() {
        return manager;
    }

    @Override
    public String getUid() {
        return uid;
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public Device getDevice() {
        return device;
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public SparseFormat getSparseFormat() {
        return sparseFormat;
    }

    @Override
    public boolean isSparse() {
        return sparseFormat != SparseFormat.DENSE;
    }

    @Override
    public NDArray asInDevice(Device device, boolean copy) {
        return this;
    }

    @Override
    public NDArray asType(DataType dtype, boolean copy) {
        return this;
    }

    @Override
    public Matrix asMatrix() {
        return null;
    }

    @Override
    public void attachGradient() {}

    @Override
    public NDArray getGradient() {
        return null;
    }

    @Override
    public ByteBuffer toByteBuffer() {
        data.rewind();
        return data;
    }

    @Override
    public void set(Buffer data) {
        int size = data.remaining();
        DataType inputType = DataType.fromBuffer(data);

        int numOfBytes = inputType.getNumOfBytes();
        this.data = ByteBuffer.allocate(size * numOfBytes);

        switch (inputType) {
            case FLOAT32:
                this.data.asFloatBuffer().put((FloatBuffer) data);
                break;
            case FLOAT64:
                this.data.asDoubleBuffer().put((DoubleBuffer) data);
                break;
            case INT8:
                this.data.put((ByteBuffer) data);
                break;
            case INT32:
                this.data.asIntBuffer().put((IntBuffer) data);
                break;
            case INT64:
                this.data.asLongBuffer().put((LongBuffer) data);
                break;
            case UINT8:
            case FLOAT16:
            default:
                throw new AssertionError("Show never happen");
        }
    }

    @Override
    public void set(NDIndex index, NDArray value) {}

    @Override
    public void set(NDIndex index, Number value) {}

    @Override
    public void setScalar(NDIndex index, Number value) {}

    @Override
    public NDArray get(NDIndex index) {
        NDIndexFixed ie = (NDIndexFixed) index.get(0);
        int idx = (int) ie.getIndex();

        Shape subShape = shape.slice(1);
        int size = (int) subShape.size() * dataType.getNumOfBytes();
        int start = idx * size;
        data.position(start);
        Buffer buf = data.slice().limit(size);
        MockNDArray array = new MockNDArray(manager, device, subShape, dataType, sparseFormat);
        array.set(buf);
        return array;
    }

    @Override
    public void copyTo(NDArray array) {}

    @Override
    public NDArray zerosLike() {
        return null;
    }

    @Override
    public NDArray onesLike() {
        return null;
    }

    @Override
    public NDArray like() {
        return null;
    }

    @Override
    public boolean contentEquals(Number number) {
        return false;
    }

    @Override
    public boolean contentEquals(NDArray other) {
        return false;
    }

    @Override
    public boolean allClose(NDArray other, float rtol, float atol, boolean equalNan) {
        return false;
    }

    @Override
    public NDArray eq(Number other) {
        return null;
    }

    @Override
    public NDArray eq(NDArray other) {
        return null;
    }

    @Override
    public NDArray neq(Number other) {
        return null;
    }

    @Override
    public NDArray neq(NDArray other) {
        return null;
    }

    @Override
    public NDArray gt(Number other) {
        return null;
    }

    @Override
    public NDArray gt(NDArray other) {
        return null;
    }

    @Override
    public NDArray gte(Number other) {
        return null;
    }

    @Override
    public NDArray gte(NDArray other) {
        return null;
    }

    @Override
    public NDArray lt(Number other) {
        return null;
    }

    @Override
    public NDArray lt(NDArray other) {
        return null;
    }

    @Override
    public NDArray lte(Number other) {
        return null;
    }

    @Override
    public NDArray lte(NDArray other) {
        return null;
    }

    @Override
    public NDArray where(NDArray condition, NDArray other) {
        return null;
    }

    @Override
    public NDArray add(Number n) {
        return null;
    }

    @Override
    public NDArray add(NDArray... others) {
        return null;
    }

    @Override
    public NDArray sub(Number n) {
        return null;
    }

    @Override
    public NDArray sub(NDArray other) {
        return null;
    }

    @Override
    public NDArray mul(Number n) {
        return null;
    }

    @Override
    public NDArray mul(NDArray... others) {
        return null;
    }

    @Override
    public NDArray div(Number n) {
        return null;
    }

    @Override
    public NDArray div(NDArray other) {
        return null;
    }

    @Override
    public NDArray mod(Number n) {
        return null;
    }

    @Override
    public NDArray mod(NDArray other) {
        return null;
    }

    @Override
    public NDArray pow(Number n) {
        return null;
    }

    @Override
    public NDArray pow(NDArray other) {
        return null;
    }

    @Override
    public NDArray addi(Number n) {
        return null;
    }

    @Override
    public NDArray addi(NDArray... others) {
        return null;
    }

    @Override
    public NDArray subi(Number n) {
        return null;
    }

    @Override
    public NDArray subi(NDArray other) {
        return null;
    }

    @Override
    public NDArray muli(Number n) {
        return null;
    }

    @Override
    public NDArray muli(NDArray... others) {
        return null;
    }

    @Override
    public NDArray divi(Number n) {
        return null;
    }

    @Override
    public NDArray divi(NDArray other) {
        return null;
    }

    @Override
    public NDArray modi(Number n) {
        return null;
    }

    @Override
    public NDArray modi(NDArray other) {
        return null;
    }

    @Override
    public NDArray powi(Number n) {
        return null;
    }

    @Override
    public NDArray powi(NDArray other) {
        return null;
    }

    @Override
    public NDArray neg() {
        return null;
    }

    @Override
    public NDArray negi() {
        return null;
    }

    @Override
    public NDArray abs() {
        Number[] values = toArray();
        switch (dataType) {
            case FLOAT64:
                {
                    double[] newBuf = new double[values.length];
                    for (int i = 0; i < values.length; ++i) {
                        newBuf[i] = Math.abs(values[i].doubleValue());
                    }
                    return manager.create(newBuf, shape);
                }
            case FLOAT32:
                {
                    float[] newBuf = new float[values.length];
                    for (int i = 0; i < values.length; ++i) {
                        newBuf[i] = Math.abs(values[i].floatValue());
                    }
                    return manager.create(newBuf, shape);
                }
            case INT64:
                {
                    long[] newBuf = new long[values.length];
                    for (int i = 0; i < values.length; ++i) {
                        newBuf[i] = Math.abs(values[i].longValue());
                    }
                    return manager.create(newBuf, shape);
                }
            case INT32:
                {
                    int[] newBuf = new int[values.length];
                    for (int i = 0; i < values.length; ++i) {
                        newBuf[i] = Math.abs(values[i].intValue());
                    }
                    return manager.create(newBuf, shape);
                }
            default:
                return null;
        }
    }

    @Override
    public NDArray square() {
        return null;
    }

    @Override
    public NDArray cbrt() {
        return null;
    }

    @Override
    public NDArray floor() {
        return null;
    }

    @Override
    public NDArray ceil() {
        return null;
    }

    @Override
    public NDArray round() {
        return null;
    }

    @Override
    public NDArray trunc() {
        return null;
    }

    @Override
    public NDArray exp() {
        return null;
    }

    @Override
    public NDArray log() {
        return null;
    }

    @Override
    public NDArray log10() {
        return null;
    }

    @Override
    public NDArray log2() {
        return null;
    }

    @Override
    public NDArray sin() {
        return null;
    }

    @Override
    public NDArray cos() {
        return null;
    }

    @Override
    public NDArray tan() {
        return null;
    }

    @Override
    public NDArray asin() {
        return null;
    }

    @Override
    public NDArray acos() {
        return null;
    }

    @Override
    public NDArray atan() {
        return null;
    }

    @Override
    public NDArray sinh() {
        return null;
    }

    @Override
    public NDArray cosh() {
        return null;
    }

    @Override
    public NDArray tanh() {
        return null;
    }

    @Override
    public NDArray asinh() {
        return null;
    }

    @Override
    public NDArray acosh() {
        return null;
    }

    @Override
    public NDArray atanh() {
        return null;
    }

    @Override
    public NDArray toDegrees() {
        return null;
    }

    @Override
    public NDArray toRadians() {
        return null;
    }

    @Override
    public NDArray max() {
        return null;
    }

    @Override
    public NDArray max(int[] axes, boolean keepDims) {
        return null;
    }

    @Override
    public NDArray min() {
        return null;
    }

    @Override
    public NDArray min(int[] axes, boolean keepDims) {
        return null;
    }

    @Override
    public NDArray sum() {
        return null;
    }

    @Override
    public NDArray sum(int[] axes, boolean keepDims) {
        return null;
    }

    @Override
    public NDArray prod() {
        return null;
    }

    @Override
    public NDArray prod(int[] axes, boolean keepDims) {
        return null;
    }

    @Override
    public NDArray mean() {
        return null;
    }

    @Override
    public NDArray mean(int[] axes, boolean keepDims) {
        return null;
    }

    @Override
    public NDArray trace(int offset, int axis1, int axis2) {
        return null;
    }

    @Override
    public NDList split(int sections, int axis) {
        NDList list = new NDList();
        for (int i = 0; i < sections; i++) {
            list.add(new MockNDArray(manager, device, getShape().slice(1), dataType, sparseFormat));
        }
        return list;
    }

    @Override
    public NDList split(int[] indices, int axis) {
        return null;
    }

    @Override
    public NDArray flatten() {
        return null;
    }

    @Override
    public NDArray reshape(Shape shape) {
        return null;
    }

    @Override
    public NDArray expandDims(int axis) {
        return null;
    }

    @Override
    public NDArray squeeze(int[] axes) {
        return null;
    }

    @Override
    public NDArray stack(NDList arrays, int axis) {
        Shape newShape = new Shape(arrays.size() + 1).addAll(getShape());
        return new MockNDArray(manager, device, newShape, dataType, sparseFormat);
    }

    @Override
    public NDArray concat(NDList arrays, int axis) {
        return null;
    }

    @Override
    public NDArray logicalAnd(NDArray other) {
        return null;
    }

    @Override
    public NDArray logicalOr(NDArray other) {
        return null;
    }

    @Override
    public NDArray logicalXor(NDArray other) {
        return null;
    }

    @Override
    public NDArray logicalNot() {
        return null;
    }

    @Override
    public NDArray argsort(int axis, boolean ascending) {
        return null;
    }

    @Override
    public NDArray sort(int axis) {
        return null;
    }

    @Override
    public NDArray sort() {
        return null;
    }

    @Override
    public NDArray softmax(int[] axes, double temperature) {
        return null;
    }

    @Override
    public NDArray cumsumi(int axis) {
        return null;
    }

    @Override
    public NDArray cumsumi() {
        return null;
    }

    @Override
    public NDArray cumsum(int axis) {
        return null;
    }

    @Override
    public NDArray cumsum() {
        return null;
    }

    @Override
    public NDArray toSparse(SparseFormat fmt) {
        return null;
    }

    @Override
    public NDArray toDense() {
        return null;
    }

    @Override
    public NDArray isInfinite() {
        return null;
    }

    @Override
    public NDArray isNaN() {
        return null;
    }

    @Override
    public NDArray createMask(NDIndex index) {
        return null;
    }

    @Override
    public NDArray createMask(Predicate<Number> predicate) {
        return null;
    }

    @Override
    public NDArray tile(long repeats) {
        return null;
    }

    @Override
    public NDArray tile(int axis, long repeats) {
        return null;
    }

    @Override
    public NDArray tile(long[] repeats) {
        return null;
    }

    @Override
    public NDArray tile(Shape desiredShape) {
        return null;
    }

    @Override
    public NDArray repeat(long repeats) {
        return null;
    }

    @Override
    public NDArray repeat(int axis, long repeats) {
        return null;
    }

    @Override
    public NDArray repeat(long[] repeats) {
        return null;
    }

    @Override
    public NDArray repeat(Shape desiredShape) {
        return null;
    }

    @Override
    public NDArray dot(NDArray other) {
        return null;
    }

    @Override
    public NDArray clip(double min, double max) {
        return null;
    }

    @Override
    public NDArray swapAxes(int axis1, int axis2) {
        return null;
    }

    @Override
    public NDArray transpose() {
        return null;
    }

    @Override
    public NDArray transpose(int... dimensions) {
        return null;
    }

    @Override
    public NDArray broadcast(Shape shape) {
        return null;
    }

    @Override
    public boolean equalShapes(NDArray other) {
        return false;
    }

    @Override
    public NDArray argmax() {
        return null;
    }

    @Override
    public NDArray argmax(int axis) {
        return null;
    }

    @Override
    public NDArray argmin() {
        return null;
    }

    @Override
    public NDArray argmin(int axis) {
        return null;
    }

    @Override
    public Number percentileNumber(Number percentile) {
        return null;
    }

    @Override
    public Number medianNumber() {
        return null;
    }

    @Override
    public NDArray median(int... dimension) {
        return null;
    }

    @Override
    public NDArray percentile(Number percentile, int... dimension) {
        return null;
    }

    @Override
    public long nonzero() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public NDArrayEx getNDArrayInternal() {
        return null;
    }

    @Override
    public void close() {}
}