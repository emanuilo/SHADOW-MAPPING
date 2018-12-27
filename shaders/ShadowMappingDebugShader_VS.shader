#version 430 core
layout(location = 0) in vec4 vertexPosition; //we can say vec4 even though we're sending vec3, graphics hardware will fill it in with ones
layout(location = 1) in vec2 _textureCoords;

out vec2 textureCoords;

void main()
{
	textureCoords = _textureCoords;
	gl_Position = vertexPosition;
}
