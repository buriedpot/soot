package soot.jimple.spark.ondemand.genericutil;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2007 Manu Sridharan
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import java.util.Collection;
import java.util.Set;

public class ArraySetMultiMap<K, V> extends AbstractMultiMap<K, V> {

  public static final ArraySetMultiMap EMPTY = new ArraySetMultiMap<Object, Object>() {

    @Override
    public boolean put(Object key, Object val) {
      throw new RuntimeException();
    }

    @Override
    public boolean putAll(Object key, Collection<? extends Object> vals) {
      throw new RuntimeException();
    }

  };

  public ArraySetMultiMap() {
    super(false);
  }

  public ArraySetMultiMap(boolean create) {
    super(create);
  }

  @Override
  protected Set<V> createSet() {
    return new ArraySet<>();
  }

  @Override
  protected Set<V> emptySet() {
    return ArraySet.<V>empty();
  }

  @Override
  public ArraySet<V> get(K key) {
    return (ArraySet<V>) super.get(key);
  }
}