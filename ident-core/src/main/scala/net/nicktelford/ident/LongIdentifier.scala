package net.nicktelford.ident

/** An Identifier that's represented as an 8-byte signed integer (Long) */
case class LongIdentifier(value: Long) extends Identifier[LongIdentifier] {
  val bytes = value.toString.getBytes
  val asString = value.toString
  override val length = 8
}
