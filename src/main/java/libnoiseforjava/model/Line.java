/*
 * Copyright (C) 2003, 2004 Jason Bevins (original libnoise code)
 * Copyright � 2010 Thomas J. Hodge (java port of libnoise)
 * 
 * This file is part of libnoiseforjava.
 * 
 * libnoiseforjava is a Java port of the C++ library libnoise, which may be found at 
 * http://libnoise.sourceforge.net/.  libnoise was developed by Jason Bevins, who may be 
 * contacted at jlbezigvins@gmzigail.com (for great email, take off every 'zig').
 * Porting to Java was done by Thomas Hodge, who may be contacted at
 * libnoisezagforjava@gzagmail.com (remove every 'zag').
 * 
 * libnoiseforjava is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * libnoiseforjava is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * libnoiseforjava.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package libnoiseforjava.model;

import libnoiseforjava.module.ModuleBase;

public class Line {

	// / Model that defines the displacement of a line segment.
	// /
	// / This model returns an output value from a noise module given the
	// / one-dimensional coordinate of an input value located on a line
	// / segment, which can be used as displacements.
	// /
	// / This class is useful for creating:
	// / - roads and rivers
	// / - disaffected college students
	// /
	// / To generate an output value, pass an input value between 0.0 and 1.0
	// / to the getValue() method. 0.0 represents the start position of the
	// / line segment and 1.0 represents the end position of the line segment.

	// / A flag that specifies whether the value is to be attenuated
	// / (moved toward 0.0) as the ends of the line segment are approached.
	boolean attenuate;

	// / A pointer to the noise module used to generate the output values.
	ModuleBase module;

	// / @a x coordinate of the start of the line segment.
	double x0;

	// / @a x coordinate of the end of the line segment.
	double x1;

	// / @a y coordinate of the start of the line segment.
	double y0;

	// / @a y coordinate of the end of the line segment.
	double y1;

	// / @a z coordinate of the start of the line segment.
	double z0;

	// / @a z coordinate of the end of the line segment.
	double z1;

	Line() {
		attenuate = true;
		module = new ModuleBase(1);
		x0 = 0.0;
		x1 = 1.0;
		y0 = 0.0;
		y1 = 1.0;
		z0 = 0.0;
		z1 = 1.0;
	}

	Line(ModuleBase module) {
		attenuate = true;
		this.module = module;
		x0 = 0.0;
		x1 = 1.0;
		y0 = 0.0;
		y1 = 1.0;
		z0 = 0.0;
		z1 = 1.0;
	}

	// / Returns the output value from the noise module given the
	// / one-dimensional coordinate of the specified input value located
	// / on the line segment.
	// /
	// / @param p The distance along the line segment (ranges from 0.0
	// / to 1.0)
	// /
	// / @returns The output value from the noise module.
	// /
	// / @pre A noise module was passed to the setModule() method.
	// / @pre The start and end points of the line segment were specified.
	// /
	// / The output value is generated by the noise module passed to the
	// / setModule() method. This value may be attenuated (moved toward
	// / 0.0) as @a p approaches either end of the line segment; this is
	// / the default behavior.
	// /
	// / If the value is not to be attenuated, @a p can safely range
	// / outside the 0.0 to 1.0 range; the output value will be
	// / extrapolated along the line that this segment is part of.
	public double getValue(double p) {
		assert (module != null);

		double x = (x1 - x0) * p + x0;
		double y = (y1 - y0) * p + y0;
		double z = (z1 - z0) * p + z0;
		double value = module.getValue(x, y, z);

		if (attenuate)
			return p * (1.0 - p) * 4 * value;
		else
			return value;
	}

	// / Returns a flag indicating whether the output value is to be
	// / attenuated (moved toward 0.0) as the ends of the line segment are
	// / approached by the input value.
	// /
	// / @returns
	// / - @a true if the value is to be attenuated
	// / - @a false if not.
	public boolean getAttenuate() {
		return attenuate;
	}

	// / Returns the noise module that is used to generate the output
	// / values.
	// /
	// / @returns A reference to the noise module.
	// /
	// / @pre A noise module was passed to the setModule() method.
	public ModuleBase getModule() {
		assert (module != null);
		return module;
	}

	// / Sets a flag indicating that the output value is to be attenuated
	// / (moved toward 0.0) as the ends of the line segment are approached.
	// /
	// / @param att A flag that specifies whether the output value is to be
	// / attenuated.
	public void setAttenuate(boolean att) {
		attenuate = att;
	}

	// / Sets the position ( @a x, @a y, @a z ) of the end of the line
	// / segment to choose values along.
	// /
	// / @param x x coordinate of the end position.
	// / @param y y coordinate of the end position.
	// / @param z z coordinate of the end position.
	public void setEndPoint(double x, double y, double z) {
		x1 = x;
		y1 = y;
		z1 = z;
	}

	// / Sets the noise module that is used to generate the output values.
	// /
	// / @param module The noise module that is used to generate the output
	// / values.
	// /
	// / This noise module must exist for the lifetime of this object,
	// / until you pass a new noise module to this method.
	public void setModule(ModuleBase module) {
		this.module = module;
	}

	// / Sets the position ( @a x, @a y, @a z ) of the start of the line
	// / segment to choose values along.
	// /
	// / @param x x coordinate of the start position.
	// / @param y y coordinate of the start position.
	// / @param z z coordinate of the start position.
	public void setStartPoint(double x, double y, double z) {
		x0 = x;
		y0 = y;
		z0 = z;
	}

	public double getX0() {
		return x0;
	}

	public double getX1() {
		return x1;
	}

	public double getY0() {
		return y0;
	}

	public double getY1() {
		return y1;
	}

	public double getZ0() {
		return z0;
	}

	public double getZ1() {
		return z1;
	}

	public void setX0(double x0) {
		this.x0 = x0;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public void setY0(double y0) {
		this.y0 = y0;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public void setZ0(double z0) {
		this.z0 = z0;
	}

	public void setZ1(double z1) {
		this.z1 = z1;
	}

}
