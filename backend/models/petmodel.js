import mongoose from "mongoose";

const petschema = new mongoose.Schema({
  name: { type: String, required: true },
  animal: { type: String, required: true },
  age: { type: String, required: true },
  location: { type: String, required: true },
  gender: { type: String, required: true },
  image: { type: String, required: true },
});

// Use "pet" as the model name to match the reference in the form schema
const petModel = mongoose.models.pet || mongoose.model("pet", petschema);

export default petModel;
