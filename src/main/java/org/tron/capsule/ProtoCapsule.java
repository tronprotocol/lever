package org.tron.capsule;

public interface ProtoCapsule<T> {

  byte[] getData();

  T getInstance();
}
