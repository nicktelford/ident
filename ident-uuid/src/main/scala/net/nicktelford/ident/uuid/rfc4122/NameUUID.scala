package net.nicktelford.ident.uuid.rfc4122

import java.security.MessageDigest
import net.nicktelford.ident.uuid._

/** Companion object for generating NameUUIDs. */
object NameUUID {

  /** Generates a NameUUID using the MD5 hashing algorithm. */
  object md5 extends NameUUIDFactory(MD5Scheme)

  /** Generates a NameUUID using the SHA1 hashing algorithm. */
  object sha1 extends NameUUIDFactory(SHA1Scheme)

  /** Generates a NameUUID using the default hashing algorithm. */
  def apply = sha1

  /** Base implementation for a NameUUID hashing scheme. */
  case class Scheme private[NameUUID] (version: Short, algorithm: String) {
    def hash(data: Iterable[Byte]): Iterable[Byte] = {
      MessageDigest.getInstance(algorithm).digest(data.toArray)
    }
  }

  /** MD5 NameUUID hashing scheme */
  case object MD5Scheme extends Scheme(3, "MD5")

  /** SHA1 NameUUID hashing scheme */
  case object SHA1Scheme extends Scheme(5, "SHA1")

  /** Factory for NameUUIDs. */
  class NameUUIDFactory private[NameUUID] (scheme: Scheme)
    extends RFC4122Factory[NameUUID] {

    /** @see UUIDFactory.MaxValue */
    val MaxValue: NameUUID = new NameUUID(encodeMSB(-1), encodeLSB(-1, -1), scheme)

    /** @see UUIDFactory.MinValue */
    val MinValue: NameUUID = new NameUUID(encodeMSB(0), encodeLSB(0, 0), scheme)

    /** @see RFC4122.version */
    val version: Short = scheme.version

    /** Generates a UUID for the given name using the given Scheme. */
    def apply(namespace: UUID, name: Iterable[Byte]): NameUUID = {
      val hash = scheme.hash(namespace.bytes ++ name)

      val timestampBits = bytesToLong(hash)
      val clockBits = bytesToInt(hash.slice(8, 12))
      val nodeBits = bytesToLong(hash.slice(12, 16))

      val msb = encodeMSB(timestampBits)
      val lsb = encodeLSB(clockBits, nodeBits)

      new NameUUID(msb, lsb, scheme)
    }

    def apply(msb: Long, lsb: Long): NameUUID = {
      NameUUID(msb, lsb, scheme)
    }

    // TODO: using a fold isn't very performant, although it is expressive
    /** Generates a Long from the first 8 bytes of a byte array. */
    private def bytesToLong(bytes: Iterable[Byte]): Long = {
      bytes.take(8).zipWithIndex.foldLeft(0L) {
        case (acc, (b, i)) => acc | (b << (7 - i) * 8)
      }
    }

    // TODO: using a fold isn't very performant, although it is expressive
    /** Generates an Int from the first 4 bytes of a byte array. */
    private def bytesToInt(bytes: Iterable[Byte]): Int = {
      bytes.take(4).zipWithIndex.foldLeft(0) {
        case (acc, (b, i)) => acc | (b << (7 - i) * 8)
      }
    }
  }

}


/** A Version 3 or 5, name-based UUID hashed from a namespace and name. */
case class NameUUID(msb: Long, lsb: Long, scheme: NameUUID.Scheme)
  extends UUID with RFC4122 {

  val version: Short = scheme.version
}
