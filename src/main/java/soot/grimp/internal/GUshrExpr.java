package soot.grimp.internal;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 1999 Patrick Lam
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

import soot.IntType;
import soot.LongType;
import soot.Type;
import soot.UnknownType;
import soot.Value;
import soot.grimp.Grimp;
import soot.jimple.ExprSwitch;
import soot.jimple.UshrExpr;
import soot.util.Switch;

public class GUshrExpr extends AbstractGrimpIntLongBinopExpr implements UshrExpr {

  public GUshrExpr(Value op1, Value op2) {
    super(op1, op2);
  }

  @Override
  public String getSymbol() {
    return " >>> ";
  }

  @Override
  public int getPrecedence() {
    return 650;
  }

  @Override
  public void apply(Switch sw) {
    ((ExprSwitch) sw).caseUshrExpr(this);
  }

  @Override
  public Type getType() {
    if (isIntLikeType(op2Box.getValue().getType())) {
      final Type t1 = op1Box.getValue().getType();
      if (isIntLikeType(t1)) {
        return IntType.v();
      }
      final LongType tyLong = LongType.v();
      if (tyLong.equals(t1)) {
        return LongType.v();
      }
    }
    return UnknownType.v();
  }

  @Override
  public Object clone() {
    return new GUshrExpr(Grimp.cloneIfNecessary(getOp1()), Grimp.cloneIfNecessary(getOp2()));
  }
}
