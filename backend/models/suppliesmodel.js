import mongoose from "mongoose";

const petSuppliesSchema = new mongoose.Schema({
  id: { type: Number, required: true, unique: true }, // Unique identifier for the supply item
  name: { type: String, required: true }, // Name of the supply item
  price: { type: Number, required: true }, // Price of the supply item
  category: { type: String, required: true }, // Category of the supply (e.g., Food Bowls, Carrier Bags)
  material: { type: String, required: false }, // Material of the supply item (optional)
  img: { type: String, required: true }, // Image URL for the supply item
});

const PetSuppliesModel =
  mongoose.models.petSupplies ||
  mongoose.model("petSupplies", petSuppliesSchema);

export default PetSuppliesModel;
