package net.nicktelford.ident.uuid

/** Generic factory for a UUID implementation */
trait UUIDFactory[A <: UUID] {

  /** Variant of UUID this UUIDFactory generates. */
  def variant: Variant

  /** Generates the maximum possible UUID for this version. */
  def MaxValue: A

  /** Generates the minimum possible UUID for this version. */
  def MinValue: A
}
