/*
 * Copyright (C) 2016 guoh.io. All rights reserved.
 */
package com.maogh.app.service;

import java.io.Serializable;

import com.maogh.app.model.GenericEntity;

/**
 * TODO: DOCUMENT ME!
 * 
 * @author mao_g
 */
public interface GenericService<T extends GenericEntity, PK extends Serializable> {
  T getById(PK id);

  T update(T t);

  void deleteById(PK id);

  T insert(T t);
}
