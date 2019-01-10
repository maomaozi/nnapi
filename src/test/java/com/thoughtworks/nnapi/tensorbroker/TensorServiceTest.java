package com.thoughtworks.nnapi.tensorbroker;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class TensorServiceTest {

    private ComputeEngine mockedComputeEngine = mock(ComputeEngine.class);

    @Test
    public void getInstanceShouldReturnCorrect() throws Exception{
        ComputeEngine ce = TensorService.getInstance(mockedComputeEngine.getClass().getName());
        assertThat(ce, is(notNullValue()));
    }

    @Test(expected = ClassNotFoundException.class)
    public void getInstanceShouldFailedWithUncompatiableClass() throws Exception {
        TensorService.getInstance(String.class.getName());
    }
}