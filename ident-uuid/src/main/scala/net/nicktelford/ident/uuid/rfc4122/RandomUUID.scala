package net.nicktelford.ident.uuid.rfc4122

import net.nicktelford.ident.uuid._

/** Companion object for generating RandomUUIDs. */
object RandomUUID extends UUIDFactory[RandomUUID] with RFC4122Factory {

  /** @see UUIDFactory.MaxValue */
  val MaxValue: RandomUUID = RandomUUID(encodeMSB(-1L), encodeLSB(-1, -1))

  /** @see UUIDFactory.MinValue */
  val MinValue: RandomUUID = RandomUUID(encodeMSB(0), encodeLSB(0, 0))

  /** @see RFC4122Factory.version */
  val version: Short = 4

  /** Generates a RandomUUID */
  def apply: RandomUUID = RandomUUID(randomMSB, randomLSB)
}

/** A Version 4, randomly generated UUID. */
case class RandomUUID(msb: Long, lsb: Long) extends UUID with RFC4122 {

  /** @see RFC4122.version */
  val version: Short = 4
}
