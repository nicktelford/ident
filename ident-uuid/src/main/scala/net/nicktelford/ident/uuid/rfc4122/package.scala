package net.nicktelford.ident.uuid

/** Implicits for RFC4122 UUIDs */
package object rfc4122 {

  // provide all the default UUID implementations in implicit scope
  implicit val TimeUUIDFactory = TimeUUID
  implicit val DCEUUIDFactory = DCEUUID
  implicit val RandomUUIDFactory = RandomUUID
  // TODO: separate factories in to different classes
  implicit val MD5UUIDFactory = NameUUID.md5
  implicit val SHA1UUIDFactory = NameUUID.sha1

}
