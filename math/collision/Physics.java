package math.collision;

import math.Utils.MathUtils;
import math.vecmat.Vec;
import math.vecmat.Vec2;
import math.vecmat.Vec3;

public class Physics {

	public static Vec3 intersect(Vec3 p1, Vec3 p2, Vec3 p3, Vec3 point, double dist, double border) {

		Vec3 o = p1;
		Vec3 s = p2.sub(o);
		Vec3 t = p3.sub(o);
		double dot = s.dot(t);
		if (dot != 0) {
			o = p3;
			s = p2.sub(o);
			t = p1.sub(o);
			dot = s.dot(t);
		}
		if (dot != 0) {
			o = p2;
			s = p3.sub(o);
			t = p1.sub(o);
			dot = s.dot(t);
		}
		if (dot != 0)
			System.out.println("Warning, not orthogonal axes, dot:" + dot);
		Vec3 n = s.cross(t);
		Vec3 p = point.sub(o);

		double d = n.dot(p);
		double pS = s.dot(p);
		double pT = t.dot(p);
		if (Math.abs(d) >= dist)
			return null;
		double borderS = border / s.pNorm(2), borderT = border / t.pNorm(2);
		if (pS > 0 - borderS && pT > 0 - borderT && pS + pT < 1)
			return o.addScaled(s, pS).addScaled(t, pT);
		return null;
	}

	public static Vec3 intersectRect(Vec3 p1, Vec3 p2, Vec3 p3, Vec3 point, double dist) {

		Vec3 o = p1;
		Vec3 s = p2.sub(o);
		Vec3 t = p3.sub(o);
		double dot = s.dot(t);
		if (dot != 0) {
			o = p3;
			s = p2.sub(o);
			t = p1.sub(o);
			dot = s.dot(t);
		}
		if (dot != 0) {
			o = p2;
			s = p3.sub(o);
			t = p1.sub(o);
			dot = s.dot(t);
		}
		if (dot != 0)
			System.out.println("Warning, not orthogonal axes, dot:" + dot);
		Vec3 n = s.cross(t);
		Vec3 p = point.sub(o);

		double d = n.dot(p);
		double pS = s.dot(p);
		double pT = t.dot(p);
		if (Math.abs(d) >= dist)
			return null;
		pS = Math.max(0, Math.min(1, pS));
		pT = Math.max(0, Math.min(1, pT));
		Vec3 bp = o.addScaled(s, pS).addScaled(t, pT);
		if (bp.distanceSmaller(point, dist))
			return bp;
		return null;
	}

	public static double intersectionPos(Vec2 gAp1, Vec2 gAp2, Vec2 gBp1, Vec2 gBp2) {
		double x1 = gAp1.x(), x2 = gAp2.x(), x3 = gBp1.x(), x4 = gBp2.x();
		double y1 = gAp1.y(), y2 = gAp2.y(), y3 = gBp1.y(), y4 = gBp2.y();
		double xs1 = x2 - x1, xs2 = x4 - x3;
		double ys1 = y2 - y1, ys2 = y4 - y3;
		double t3 = ys2 * xs1 - xs2 * ys1;
		if (t3 == 0)
			return -1;
		double t1 = x2 * y1 - x1 * y2;
		double t2 = x4 * y3 - x3 * y4;
		double x = (xs2 * t1 - xs1 * t2) / t3;
		double y = (ys2 * t1 - ys1 * t2) / t3;
	}

	public static Vec2 intersection(Vec2 gAp1, Vec2 gAp2, Vec2 gBp1, Vec2 gBp2) {
		double x1 = gAp1.x(), x2 = gAp2.x(), x3 = gBp1.x(), x4 = gBp2.x();
		double y1 = gAp1.y(), y2 = gAp2.y(), y3 = gBp1.y(), y4 = gBp2.y();
		double xs1 = x2 - x1, xs2 = x4 - x3;
		double ys1 = y2 - y1, ys2 = y4 - y3;
		double t3 = ys2 * xs1 - xs2 * ys1;
		if (t3 == 0)
			return null;
		double t1 = x2 * y1 - x1 * y2;
		double t2 = x4 * y3 - x3 * y4;
		double x = (xs2 * t1 - xs1 * t2) / t3;
		double y = (ys2 * t1 - ys1 * t2) / t3;
		Vec2 res = Vec.Vec2(x, y);
		if (res.withinRectangle(gAp1, gAp2) && res.withinRectangle(gBp1, gBp2))
			return res;
		return null;
	}

	public static Vec2 intersect2DLineWTriangleBorders(Vec2 start, Vec2 end, Vec2 pA, Vec2 pB, Vec2 pC) {
		Vec2 dir = end.sub(start);
		Vec2 A = pA.sub(start);
		Vec2 B = pB.sub(start);
		Vec2 C = pC.sub(start);

		Vec2 res;
		if ((res = intersection(Vec.Vec2(), dir, A, B)) != null)
			return res;
		if ((res = intersection(Vec.Vec2(), dir, B, C)) != null)
			return res;
		if ((res = intersection(Vec.Vec2(), dir, C, A)) != null)
			return res;

	}
}
