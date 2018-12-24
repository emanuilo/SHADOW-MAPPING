#version 430
in vec4 interpolatedVertexColor;
out vec4 outColor;
void main()
{
	outColor = interpolatedVertexColor;
}
