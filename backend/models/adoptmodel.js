import mongoose from "mongoose";

const formschema = new mongoose.Schema(
  {
    pettoadopt: {
      type: mongoose.Types.ObjectId,
      ref: "pet", // Reference to the Pet model
    },
    name: { type: String, required: true },
    email: { type: String, required: true },
    phoneNo: { type: String, required: true },
    livingSituation: { type: String, required: true },
    previousPets: { type: String },
    otherPets: { type: String },
    status: { type: String, default: "Pending" },
  },
  { timestamps: true }
);

const formModel = mongoose.models.form || mongoose.model("form", formschema);

export default formModel;
