import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { schema } from "./zodValidation";
import { createShortUrl } from "../../core/services";
import { useEffect, useState } from "react";

export default function Home() {
  const navigate = useNavigate();
  const [shortUrl,setShortUrl] = useState<string|undefined>(undefined)
  const {
    register,
    setValue,
    handleSubmit,
    formState: { errors },
  } = useForm({ resolver: shortUrl? undefined: zodResolver(schema), reValidateMode: "onSubmit" });

  const copyUrl = async (text:string)=> await navigator.clipboard.writeText(text)
  const onSubmit = handleSubmit(async (data) => shortUrl !== undefined ? await copyUrl(data.url): await createShortUrl(data.url).then(response => setShortUrl(response.urlShortner)));
  const goToLinkInformation = () => navigate("/information");
  useEffect(()=>{
    if(shortUrl!== undefined){
      setValue("url",shortUrl)
    }
  },[shortUrl])
  return (
    <div className="min-h-screen flex items-center justify-center bg-[linear-gradient(135deg,#f7f8fc,#e9ebf3)] px-4 font-sans">
      <div className="bg-white rounded-2xl shadow-lg max-w-5xl w-full p-12 md:flex md:items-center md:justify-between transition-shadow">
        <div className="md:flex-1">
          <h1 className="text-3xl text-blue-600 font-semibold mb-3">
            Encurtador de URL
          </h1>
          <p className="text-gray-600 mb-6">
            Transforme sua URL em um link curto e elegante.
          </p>

          <form
            onSubmit={onSubmit}
            className="bg-gray-100 rounded-lg p-2 flex items-center max-w-md"
          >
            <input
              {...register("url")}
              placeholder="Insira uma URL longa"
              className="flex-1 bg-transparent outline-none px-3 py-2 text-gray-800"
            />
            <button className="ml-3 bg-blue-600 hover:bg-blue-700 text-white rounded-lg px-4 py-2 font-medium">
              {shortUrl!==undefined? "Copiar": "Encurtar"}
            </button>
          </form>
          {errors.url && (
            <span className="text-error font-medium">{errors.url.message}</span>
          )}

          {/* {short && (
            <div className="mt-4">
              <p className="text-sm text-gray-700">Link encurtado:</p>
              <a href={short} className="text-blue-600 font-medium">
                {short}
              </a>
            </div>
          )} */}

          <div className="flex justify-end mt-3">
            <span
              className="text-blue-600 font-medium cursor-default"
              onClick={goToLinkInformation}
            >
              Acompanhar engajamento
            </span>
          </div>
        </div>

        <div className="md:flex-1 mt-8 md:mt-0 md:pl-6 text-right">
          <img
            src="/src/assets/home-image.svg"
            alt="Ilustração de encurtador de URL"
            className="w-full max-w-md rounded-xl transform scale-105"
          />
        </div>
      </div>
    </div>
  );
}
