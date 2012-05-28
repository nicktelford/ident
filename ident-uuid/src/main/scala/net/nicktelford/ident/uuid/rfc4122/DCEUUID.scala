package net.nicktelford.ident.uuid.rfc4122

import net.nicktelford.ident.uuid.UUID

// TODO: implement DCE Security UUID algorithm, detailed here: http://pubs.opengroup.org/onlinepubs/9696989899/chap5.htm#tagcjh_08_02_01_01

object DCEUUID extends RFC4122Factory[DCEUUID] {

  /** Generates the maximum possible UUID for this version. */
  def MaxValue = new DCEUUID(encodeMSB(-1), encodeLSB(-1, -1))

  /** Generates the minimum possible UUID for this version. */
  def MinValue = new DCEUUID(encodeMSB(0), encodeLSB(0, 0))

  /** UUID version this Factory generates. */
  def version = 2
}

/** A Version 2, DCE Security UUID that encodes the POSIX UID/GID */
case class DCEUUID(msb: Long, lsb: Long) extends UUID with RFC4122 {

  val version: Short = 2
}
