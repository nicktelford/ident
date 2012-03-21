package net.nicktelford.ident

/** An Identifier that's represented as an arbitrary ASCII String */
case class ASCIIIdentifier(value: String) extends Identifier[ASCIIIdentifier] {
  val bytes = value.getBytes("ASCII")
  val asString = value
  override val length = value.length
}
