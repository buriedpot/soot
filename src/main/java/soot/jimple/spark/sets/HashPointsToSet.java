package soot.jimple.spark.sets;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2002 Ondrej Lhotak
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import soot.Type;
import soot.jimple.spark.pag.Node;
import soot.jimple.spark.pag.PAG;

/**
 * HashSet implementation of points-to set.
 *
 * @author Ondrej Lhotak
 */
public final class HashPointsToSet extends PointsToSetInternal {
  public HashPointsToSet(Type type, PAG pag) {
    super(type);
    this.pag = pag;
  }

  /** Returns true if this set contains no run-time objects. */
  @Override
  public final boolean isEmpty() {
    return s.isEmpty();
  }

  /**
   * Adds contents of other into this set, returns true if this set changed.
   */
  @Override
  public final boolean addAll(final PointsToSetInternal other, final PointsToSetInternal exclude) {
    if (other instanceof HashPointsToSet && exclude == null
        && (pag.getTypeManager().getFastHierarchy() == null || type == null || type.equals(other.type))) {
      return s.addAll(((HashPointsToSet) other).s);
    } else {
      return super.addAll(other, exclude);
    }
  }

  /** Calls v's visit method on all nodes in this set. */
  @Override
  public final boolean forall(P2SetVisitor v) {
    for (Iterator<Node> it = new ArrayList<>(s).iterator(); it.hasNext();) {
      v.visit(it.next());
    }
    return v.getReturnValue();
  }

  /** Adds n to this set, returns true if n was not already in this set. */
  @Override
  public final boolean add(Node n) {
    if (pag.getTypeManager().castNeverFails(n.getType(), type)) {

      return s.add(n);
    }
    return false;
  }

  /** Returns true iff the set contains n. */
  @Override
  public final boolean contains(Node n) {
    return s.contains(n);
  }

  public static P2SetFactory getFactory() {
    return new P2SetFactory() {
      @Override
      public PointsToSetInternal newSet(Type type, PAG pag) {
        return new HashPointsToSet(type, pag);
      }
    };
  }

  /* End of public methods. */
  /* End of package methods. */

  private final HashSet<Node> s = new HashSet<>(4);
  private PAG pag = null;
}
