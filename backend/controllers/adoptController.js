import adoptModel from "../models/adoptModel.js";
import petModel from "../models/petModel.js"; // Import petModel to handle pet deletion

// Add a new adoption form
const addforms = async (req, res) => {
  try {
    const { name, email, phoneNo, livingSituation, previousPets, otherPets, pettoadopt } = req.body;

    // Validate required fields
    if (!name || !email || !phoneNo || !livingSituation || !pettoadopt) {
      return res.status(400).json({
        success: false,
        message: "All required fields (name, email, phoneNo, livingSituation, pettoadopt) must be provided.",
      });
    }

    // Check for duplicate forms based on email and pet ID
    const duplicateForm = await adoptModel.findOne({ email, pettoadopt });
    if (duplicateForm) {
      return res.status(400).json({
        success: false,
        message: "A form with this email and pet ID already exists.",
      });
    }

    // Create a new adoption form
    const newAdopt = new adoptModel({
      name,
      email,
      phoneNo,
      livingSituation,
      previousPets: previousPets || "",
      otherPets: otherPets || "",
      pettoadopt,
      status: "Pending",
    });

    const savedForm = await newAdopt.save();
    res.status(201).json({ success: true, message: "Form added successfully", form: savedForm });
  } catch (error) {
    console.error("Error in adding form:", error);
    res.status(500).json({ success: false, message: "Internal server error" });
  }
};

// List all adoption forms
const listform = async (req, res) => {
  try {
    // Fetch all pending adoption requests and populate pet details
    const forms = await adoptModel
      .find({ status: "Pending" }) // Only fetch pending requests
      .populate("pettoadopt", "name image"); // Populate pettoadopt with pet name and image

    if (!forms || forms.length === 0) {
      return res.status(404).json({ success: false, message: "No pending forms found." });
    }

    res.status(200).json({ success: true, data: forms });
  } catch (error) {
    console.error("Error in listing forms:", error);
    res.status(500).json({ success: false, message: "Internal server error" });
  }
};

// Approve a form and delete the pet
const approveform = async (req, res) => {
  try {
    const { id } = req.params;

    // Validate ID
    if (!id) {
      return res.status(400).json({ success: false, message: "ID is required." });
    }

    // Find the adoption form and populate the associated pet
    const form = await adoptModel.findById(id).populate("pettoadopt");
    if (!form) {
      return res.status(404).json({ success: false, message: "Form not found." });
    }

    // Approve the adoption request
    form.status = "Approved";
    await form.save();

    // Delete the associated pet
    if (form.pettoadopt) {
      await petModel.findByIdAndDelete(form.pettoadopt._id);
    }

    res.status(200).json({ success: true, message: "Request approved successfully.", data: form });
  } catch (error) {
    console.error("Error approving form:", error);
    res.status(500).json({ success: false, message: "Internal server error" });
  }
};

// Remove an adoption form by ID
const removeform = async (req, res) => {
  try {
    const { id } = req.params;

    // Validate ID
    if (!id) {
      return res.status(400).json({ success: false, message: "Form ID is required." });
    }

    // Check if the form exists
    const form = await adoptModel.findById(id);
    if (!form) {
      return res.status(404).json({ success: false, message: "Form doesn't exist." });
    }

    // Delete the form
    await adoptModel.findByIdAndDelete(id);
    res.status(200).json({ success: true, message: "Form deleted successfully." });
  } catch (error) {
    console.error("Error in removing form:", error);
    res.status(500).json({ success: false, message: "Internal server error." });
  }
};

export { addforms, listform, removeform, approveform };
