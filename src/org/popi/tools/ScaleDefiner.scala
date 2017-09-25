/**
 *  Copyright (C) Emiliyan Todorov.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.popi.tools

import scala.collection.immutable.List
import org.popi.wrapper.MathUtil
import scala.collection.mutable.ListBuffer
import scala.collection.immutable.Map

/**
 *
 * Define the scales for a given data
 * @author Emiliyan Todorov
 *
 */
object ScaleDefiner {

  /**
   * Define the resolution for each scale starting from the smallest possible one : Δt=1
   * @param dataSize the size of the input data
   * @return a list with the resolution of each scale
   * </br> Example: For data with size 100 there are 6 scales with resolutions: 1, 2, 4, 8, 16, 32
   */
  def defineScaleResolutions(dataSize: Int): List[Long] = {
    val maxScaleResolution = defineMaxVarietiesOfResolutions(dataSize)
    val buffer = ListBuffer[Long]()
    for(i <- 0L until maxScaleResolution){
      buffer += MathUtil.pow(2, i).toLong
    }
    buffer.toList
  }

  /**
   * Define the size for each scale starting from the smallest possible resolution : Δt=1
   * @param dataSize the size of the input data
   * @return a map with the size of each scale's resolution, where key = resolution, value = size
   * </br> Example: For data with size 100 there are 6 scales with sizes: 100, 50, 25, 12.5, 6.25, 3.125
   * </br> corresponding to resolutions: 1, 2, 4, 8, 16, 32
   */
  def defineScaleSizes(dataSize: Int): Map[Long, Double] = {
    val resolutions = defineScaleResolutions(dataSize)
    resolutions.map(resolution => resolution -> (dataSize.toDouble / resolution.toDouble)).toMap
  }

  private def defineMaxVarietiesOfResolutions(dataSize: Int): Long = {
    val size = MathUtil.log2(dataSize.toDouble)
    size.toLong
  }
}
