# PPTX Presentation Generation

This document provides instructions on how to generate a PPTX presentation using the `create_presentation.py` script.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/SuedaSen/SoftwareQualityChecker.git
   cd SoftwareQualityChecker
   ```

2. Make sure you have Python installed (version 3.6 or higher).

3. Install the required dependencies:
   ```bash
   pip install -r requirements.txt
   ```

## Usage

To generate the PPTX presentation, follow these steps:

1. Ensure your data is prepared in the specified format. Refer to the documentation for details on the data structure.

2. Run the `create_presentation.py` script:
   ```bash
   python create_presentation.py <input_data_file>
   ```
   Replace `<input_data_file>` with the path to your data file.

3. After execution, the generated PPTX presentation will be available in the output directory specified in the script.

## Example

```bash
python create_presentation.py data.json
```

This will take `data.json` as input and create a presentation based on the data provided in that file.

## Conclusion

You can now easily generate a PPTX presentation using the instructions above. If you encounter any issues, please refer to the [issues page](https://github.com/SuedaSen/SoftwareQualityChecker/issues) or contact the repository maintainer.